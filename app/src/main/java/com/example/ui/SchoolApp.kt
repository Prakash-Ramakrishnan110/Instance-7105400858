package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.config.SchoolConfig
import com.example.data.SchoolRepository
import com.example.model.UserRole
import com.example.ui.components.*
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.admin.AdminDashboardScreen
import com.example.ui.screens.parent.ParentDashboardScreen
import com.example.ui.screens.student.*
import com.example.ui.screens.teacher.TeacherDashboardScreen
import com.example.ui.theme.*


enum class AppDestination {
    SPLASH,
    LOGIN,
    MAIN
}

enum class StudentTab(val label: String, val icon: ImageVector, val selectedIcon: ImageVector) {
    HOME("Home", Icons.Outlined.Home, Icons.Default.Home),
    HOMEWORK("Homework", Icons.Outlined.Assignment, Icons.Default.Assignment),
    ACADEMICS("Academics", Icons.Outlined.MenuBook, Icons.Default.MenuBook),
    UPDATES("Updates", Icons.Outlined.Campaign, Icons.Default.Campaign),
    PROFILE("Profile", Icons.Outlined.Person, Icons.Default.Person)
}

@Composable
fun SchoolApp() {
    val schoolConfig by SchoolRepository.schoolConfig.collectAsState()
    val currentRole by SchoolRepository.currentRole.collectAsState()
    val currentStudent by SchoolRepository.currentStudent.collectAsState()
    val homeworkList by SchoolRepository.homeworkList.collectAsState()
    val notifications by SchoolRepository.notifications.collectAsState()

    val pendingHwCount = homeworkList.count { it.status == "Pending" }
    val unreadNotifsCount = notifications.count { !it.isRead }

    var currentDestination by remember { mutableStateOf(AppDestination.SPLASH) }
    var currentStudentTab by remember { mutableStateOf(StudentTab.HOME) }
    var academicsInitialTab by remember { mutableIntStateOf(0) }
    var updatesInitialTab by remember { mutableIntStateOf(0) }

    // Contact School Dialog
    var showContactDialog by remember { mutableStateOf(false) }

    SchoolAppTheme(schoolConfig = schoolConfig) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentDestination) {
                AppDestination.SPLASH -> {
                    SplashScreen(
                        schoolConfig = schoolConfig,
                        onNavigateToLogin = {
                            currentDestination = AppDestination.LOGIN
                        }
                    )
                }

                AppDestination.LOGIN -> {
                    LoginScreen(
                        schoolConfig = schoolConfig,
                        onRoleSelected = { selectedRole ->
                            SchoolRepository.switchRole(selectedRole)
                            currentDestination = AppDestination.MAIN
                        }
                    )
                }

                AppDestination.MAIN -> {
                    Scaffold(
                        topBar = {
                            Column {
                                // Sticky Sales Demo Role Switcher
                                DemoRoleSwitcherBar(
                                    currentRole = currentRole,
                                    onRoleSelected = { role ->
                                        SchoolRepository.switchRole(role)
                                    }
                                )

                                // Institutional Top Bar
                                SchoolTopBar(
                                    schoolConfig = schoolConfig,
                                    title = schoolConfig.schoolName,
                                    subtitle = "${schoolConfig.tagline} • ${currentRole.displayName} Mode",
                                    actions = {
                                        IconButton(onClick = { showContactDialog = true }) {
                                            Icon(
                                                imageVector = Icons.Default.PhoneInTalk,
                                                contentDescription = "Contact School",
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }

                                        // Profile / Notification Avatar with Red Badge
                                        Box(
                                            modifier = Modifier
                                                .padding(end = 4.dp)
                                                .clickable {
                                                    if (currentRole == UserRole.STUDENT) {
                                                        currentStudentTab = StudentTab.PROFILE
                                                    }
                                                },
                                            contentAlignment = Alignment.TopEnd
                                        ) {
                                            Surface(
                                                modifier = Modifier.size(36.dp),
                                                shape = CircleShape,
                                                color = Slate100,
                                                border = BorderStroke(1.5.dp, Color.White),
                                                shadowElevation = 1.dp
                                            ) {
                                                Box(
                                                    contentAlignment = Alignment.Center,
                                                    modifier = Modifier.fillMaxSize()
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Person,
                                                        contentDescription = "User Profile",
                                                        tint = Slate500,
                                                        modifier = Modifier.size(20.dp)
                                                    )
                                                }
                                            }

                                            if (unreadNotifsCount > 0) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(10.dp)
                                                        .clip(CircleShape)
                                                        .background(StatusDanger)
                                                        .border(1.5.dp, Color.White, CircleShape)
                                                )
                                            }
                                        }
                                    }
                                )
                            }
                        },
                        bottomBar = {
                            if (currentRole == UserRole.STUDENT) {
                                NavigationBar(
                                    containerColor = Color.White,
                                    tonalElevation = 0.dp,
                                    modifier = Modifier
                                        .testTag("student_bottom_nav")
                                        .border(BorderStroke(1.dp, Slate200))
                                ) {
                                    StudentTab.values().forEach { tab ->
                                        val isSelected = currentStudentTab == tab
                                        val badgeCount = when (tab) {
                                            StudentTab.HOMEWORK -> pendingHwCount
                                            StudentTab.UPDATES -> unreadNotifsCount
                                            else -> 0
                                        }

                                        NavigationBarItem(
                                            selected = isSelected,
                                            onClick = {
                                                currentStudentTab = tab
                                                if (tab == StudentTab.ACADEMICS) academicsInitialTab = 0
                                                if (tab == StudentTab.UPDATES) updatesInitialTab = 0
                                            },
                                            icon = {
                                                BadgedBox(
                                                    badge = {
                                                        if (badgeCount > 0) {
                                                            Badge {
                                                                Text(if (badgeCount > 9) "9+" else "$badgeCount")
                                                            }
                                                        }
                                                    }
                                                ) {
                                                    Icon(
                                                        imageVector = if (isSelected) tab.selectedIcon else tab.icon,
                                                        contentDescription = tab.label
                                                    )
                                                }
                                            },
                                            label = {
                                                Text(
                                                    text = tab.label,
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                                                        fontSize = 10.5.sp
                                                    )
                                                )
                                            },
                                            colors = NavigationBarItemDefaults.colors(
                                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
                                                unselectedIconColor = Slate400,
                                                unselectedTextColor = Slate400
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            when (currentRole) {
                                UserRole.STUDENT -> {
                                    when (currentStudentTab) {
                                        StudentTab.HOME -> {
                                            StudentHomeScreen(
                                                schoolConfig = schoolConfig,
                                                student = currentStudent,
                                                onNavigateAction = { action ->
                                                    when (action) {
                                                        "homework" -> currentStudentTab = StudentTab.HOMEWORK
                                                        "timetable" -> {
                                                            academicsInitialTab = 0
                                                            currentStudentTab = StudentTab.ACADEMICS
                                                        }
                                                        "materials" -> {
                                                            academicsInitialTab = 1
                                                            currentStudentTab = StudentTab.ACADEMICS
                                                        }
                                                        "exams" -> {
                                                            academicsInitialTab = 2
                                                            currentStudentTab = StudentTab.ACADEMICS
                                                        }
                                                        "results" -> {
                                                            academicsInitialTab = 3
                                                            currentStudentTab = StudentTab.ACADEMICS
                                                        }
                                                        "updates" -> {
                                                            updatesInitialTab = 0
                                                            currentStudentTab = StudentTab.UPDATES
                                                        }
                                                        "events" -> {
                                                            updatesInitialTab = 2
                                                            currentStudentTab = StudentTab.UPDATES
                                                        }
                                                        "attendance" -> {
                                                            // Navigate to attendance
                                                            academicsInitialTab = 0
                                                            currentStudentTab = StudentTab.ACADEMICS
                                                        }
                                                    }
                                                }
                                            )
                                        }

                                        StudentTab.HOMEWORK -> {
                                            StudentHomeworkScreen()
                                        }

                                        StudentTab.ACADEMICS -> {
                                            StudentAcademicsScreen(initialTab = academicsInitialTab)
                                        }

                                        StudentTab.UPDATES -> {
                                            StudentUpdatesScreen(initialTab = updatesInitialTab)
                                        }

                                        StudentTab.PROFILE -> {
                                            StudentProfileScreen(
                                                schoolConfig = schoolConfig,
                                                student = currentStudent,
                                                onSwitchRole = { newRole ->
                                                    SchoolRepository.switchRole(newRole)
                                                },
                                                onSignOut = {
                                                    currentDestination = AppDestination.LOGIN
                                                }
                                            )
                                        }
                                    }
                                }

                                UserRole.PARENT -> {
                                    ParentDashboardScreen(
                                        schoolConfig = schoolConfig,
                                        student = currentStudent,
                                        onNavigateAction = { action ->
                                            when (action) {
                                                "attendance" -> {
                                                    SchoolRepository.switchRole(UserRole.STUDENT)
                                                    currentStudentTab = StudentTab.ACADEMICS
                                                }
                                                "homework" -> {
                                                    SchoolRepository.switchRole(UserRole.STUDENT)
                                                    currentStudentTab = StudentTab.HOMEWORK
                                                }
                                                "timetable" -> {
                                                    SchoolRepository.switchRole(UserRole.STUDENT)
                                                    academicsInitialTab = 0
                                                    currentStudentTab = StudentTab.ACADEMICS
                                                }
                                                "results" -> {
                                                    SchoolRepository.switchRole(UserRole.STUDENT)
                                                    academicsInitialTab = 3
                                                    currentStudentTab = StudentTab.ACADEMICS
                                                }
                                                "exams" -> {
                                                    SchoolRepository.switchRole(UserRole.STUDENT)
                                                    academicsInitialTab = 2
                                                    currentStudentTab = StudentTab.ACADEMICS
                                                }
                                                "updates" -> {
                                                    SchoolRepository.switchRole(UserRole.STUDENT)
                                                    updatesInitialTab = 0
                                                    currentStudentTab = StudentTab.UPDATES
                                                }
                                                "events" -> {
                                                    SchoolRepository.switchRole(UserRole.STUDENT)
                                                    updatesInitialTab = 2
                                                    currentStudentTab = StudentTab.UPDATES
                                                }
                                            }
                                        }
                                    )
                                }

                                UserRole.TEACHER -> {
                                    TeacherDashboardScreen(
                                        schoolConfig = schoolConfig,
                                        onNavigateAction = { action ->
                                            if (action == "timetable") {
                                                SchoolRepository.switchRole(UserRole.STUDENT)
                                                academicsInitialTab = 0
                                                currentStudentTab = StudentTab.ACADEMICS
                                            }
                                        }
                                    )
                                }

                                UserRole.ADMIN -> {
                                    AdminDashboardScreen(
                                        schoolConfig = schoolConfig,
                                        onUpdateConfig = { newConfig ->
                                            SchoolRepository.updateSchoolConfig(newConfig)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (showContactDialog) {
                ContactSchoolDialog(
                    schoolConfig = schoolConfig,
                    onDismiss = { showContactDialog = false }
                )
            }
        }
    }
}
