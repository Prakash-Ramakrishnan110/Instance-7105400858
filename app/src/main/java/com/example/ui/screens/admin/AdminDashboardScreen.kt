package com.example.ui.screens.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.config.SchoolConfig
import com.example.data.MockSchoolData
import com.example.data.SchoolRepository
import com.example.model.NotificationCategory
import com.example.model.Student
import com.example.ui.components.SchoolStatCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    schoolConfig: SchoolConfig,
    onUpdateConfig: (SchoolConfig) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Overview, 1: Students, 2: Send Notice, 3: School Branding
    var statusMessage by remember { mutableStateOf<String?>(null) }

    val tabs = listOf("Overview", "Students", "Notifications", "Branding")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("admin_dashboard_screen")
    ) {
        // Admin Header
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(1.dp, Slate200)),
            shadowElevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "School Administration",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            color = Slate900
                        )
                        Text(
                            text = "${schoolConfig.schoolName} • Principal Portal",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 13.sp
                            ),
                            color = Slate500
                        )
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Admin Mode",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // Tabs
        PrimaryTabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { idx, title ->
                Tab(
                    selected = selectedTab == idx,
                    onClick = {
                        selectedTab = idx
                        statusMessage = null
                    },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (selectedTab == idx) FontWeight.Bold else FontWeight.Medium
                            )
                        )
                    }
                )
            }
        }

        if (statusMessage != null) {
            Surface(
                color = StatusSuccessBg,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, StatusSuccess)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = StatusSuccess)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = statusMessage!!,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        color = Color(0xFF065F46)
                    )
                }
            }
        }

        when (selectedTab) {
            0 -> AdminOverviewContent(
                onNavigateTab = { selectedTab = it }
            )
            1 -> AdminStudentsDirectoryContent()
            2 -> AdminSendNotificationContent(
                onNotificationSent = { title, count ->
                    statusMessage = "Push broadcast '$title' sent to $count recipients successfully!"
                }
            )
            3 -> AdminSchoolSettingsContent(
                currentConfig = schoolConfig,
                onSave = { updated ->
                    onUpdateConfig(updated)
                    statusMessage = "School branding updated successfully! All theme colors and names refreshed."
                }
            )
        }
    }
}

@Composable
fun AdminOverviewContent(
    onNavigateTab: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Core KPIs
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SchoolStatCard(
                        title = "Total Students",
                        value = "1,248",
                        subtitle = "42 Active Classes",
                        icon = Icons.Default.Groups,
                        iconColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTab(1) }
                    )
                    SchoolStatCard(
                        title = "Faculty Teachers",
                        value = "86",
                        subtitle = "6 Departments",
                        icon = Icons.Default.School,
                        iconColor = Color(0xFF8B5CF6),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SchoolStatCard(
                        title = "Today's Attendance",
                        value = "94.2%",
                        subtitle = "1,176 Present",
                        icon = Icons.Default.CheckCircle,
                        iconColor = StatusSuccess,
                        modifier = Modifier.weight(1f)
                    )
                    SchoolStatCard(
                        title = "Broadcasts Sent",
                        value = "18",
                        subtitle = "This month",
                        icon = Icons.Default.Campaign,
                        iconColor = Color(0xFFF59E0B),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTab(2) }
                    )
                }
            }
        }

        // Quick Management Shortcuts
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, SchoolBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Text(
                        text = "Administration Hub",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary,
                        modifier = Modifier.padding(start = 4.dp, bottom = 12.dp)
                    )

                    AdminActionRow(
                        title = "Student Directory & Roster",
                        subtitle = "Browse all 1,248 student profiles and sections",
                        icon = Icons.Default.Badge,
                        onClick = { onNavigateTab(1) }
                    )
                    HorizontalDivider(color = SchoolBorder, modifier = Modifier.padding(vertical = 8.dp))
                    AdminActionRow(
                        title = "Send Push Broadcast",
                        subtitle = "Instant alert to parents, students, or staff",
                        icon = Icons.Default.NotificationsActive,
                        onClick = { onNavigateTab(2) }
                    )
                    HorizontalDivider(color = SchoolBorder, modifier = Modifier.padding(vertical = 8.dp))
                    AdminActionRow(
                        title = "Branding & School Configuration",
                        subtitle = "Customize colors, logo name, motto, and contacts",
                        icon = Icons.Default.Palette,
                        onClick = { onNavigateTab(3) }
                    )
                }
            }
        }

        // Campus Operations Summary
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, SchoolBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Campus Operations Status",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Active School Buses", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        Text("24 / 24 On Schedule", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = StatusSuccess)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Term 1 Fee Collections", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        Text("96.8% Settled", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = TextPrimary)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Campus Bio-metric Sync", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        Text("Live (Updated 2m ago)", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = StatusSuccess)
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminActionRow(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold), color = TextPrimary)
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp), color = TextSecondary)
        }

        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
    }
}

@Composable
fun AdminStudentsDirectoryContent() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedGrade by remember { mutableStateOf("All") }

    val students = MockSchoolData.studentsList

    val filteredStudents = students.filter { st ->
        val matchSearch = st.name.contains(searchQuery, ignoreCase = true) ||
                st.admissionNo.contains(searchQuery, ignoreCase = true) ||
                st.parentName.contains(searchQuery, ignoreCase = true)
        val matchGrade = selectedGrade == "All" || st.grade == selectedGrade
        matchSearch && matchGrade
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("admin_students_directory"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search by name, roll no, or parent...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = null)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_search_students"),
                shape = RoundedCornerShape(10.dp),
                singleLine = true
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("All", "10", "9", "8").forEach { gr ->
                    val isSel = selectedGrade == gr
                    FilterChip(
                        selected = isSel,
                        onClick = { selectedGrade = gr },
                        label = { Text(if (gr == "All") "All Grades" else "Grade $gr") },
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }
        }

        items(filteredStudents) { student ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, SchoolBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = student.initials,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = student.name,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = SchoolSurfaceSubtle,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "Grade ${student.fullGrade}",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }

                        Text(
                            text = "Parent: ${student.parentName} • ${student.parentPhone}",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                            color = TextSecondary
                        )
                        Text(
                            text = "Roll: #${student.rollNo} • Adm: ${student.admissionNo}",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMuted
                        )
                    }

                    Surface(
                        color = StatusSuccessBg,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "${student.attendancePct}%",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = StatusSuccess
                            ),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdminSendNotificationContent(
    onNotificationSent: (String, Int) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var targetAudience by remember { mutableStateOf("All") } // All, Parents, Teachers, Students
    var selectedCategory by remember { mutableStateOf(NotificationCategory.SCHOOL) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("admin_send_notification"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Push Broadcast Dispatcher",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
            Text(
                text = "Send real-time mobile push notifications to student and parent devices",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, SchoolBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Target Recipients", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("All", "Parents", "Students", "Teachers").forEach { aud ->
                            val isSel = targetAudience == aud
                            FilterChip(
                                selected = isSel,
                                onClick = { targetAudience = aud },
                                label = { Text(aud) },
                                shape = RoundedCornerShape(16.dp)
                            )
                        }
                    }

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Broadcast Title") },
                        placeholder = { Text("e.g. Early Dismissal Notice") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("notif_input_title"),
                        shape = RoundedCornerShape(8.dp)
                    )

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text("Message Body") },
                        placeholder = { Text("Enter detailed notification message for parents and students...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("notif_input_msg"),
                        minLines = 3,
                        shape = RoundedCornerShape(8.dp)
                    )

                    // Live Delivery Simulation Info
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.CloudDone, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Estimated Audience: 1,248 active devices ready to receive FCM payload",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Button(
                        onClick = {
                            if (title.isNotEmpty() && message.isNotEmpty()) {
                                SchoolRepository.sendNotification(
                                    title = title,
                                    message = message,
                                    category = selectedCategory
                                )
                                onNotificationSent(title, 1248)
                                title = ""
                                message = ""
                            }
                        },
                        enabled = title.isNotBlank() && message.isNotBlank(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("notif_send_button"),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Broadcast Notification Now", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}

@Composable
fun AdminSchoolSettingsContent(
    currentConfig: SchoolConfig,
    onSave: (SchoolConfig) -> Unit
) {
    var schoolName by remember { mutableStateOf(currentConfig.schoolName) }
    var shortName by remember { mutableStateOf(currentConfig.shortName) }
    var tagline by remember { mutableStateOf(currentConfig.tagline) }
    var academicYear by remember { mutableStateOf(currentConfig.academicYear) }
    var address by remember { mutableStateOf(currentConfig.address) }
    var phone by remember { mutableStateOf(currentConfig.phone) }
    var email by remember { mutableStateOf(currentConfig.email) }
    var website by remember { mutableStateOf(currentConfig.website) }

    var selectedPrimaryColor by remember { mutableStateOf(currentConfig.primaryColorHex) }
    var selectedSecondaryColor by remember { mutableStateOf(currentConfig.secondaryColorHex) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("admin_school_settings"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "White-Label Brand Customizer",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = TextPrimary
        )
        Text(
            text = "Showcase how any school's identity, colors, and crest can be integrated:",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )

        // Live Preview Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(2.dp, SchoolConfig.colorFromHex(selectedPrimaryColor))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "LIVE BRANDING PREVIEW",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                    color = SchoolConfig.colorFromHex(selectedPrimaryColor)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SchoolConfig.colorFromHex(selectedPrimaryColor)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = shortName.take(3),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = schoolName,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = TextPrimary
                        )
                        Text(
                            text = tagline,
                            style = MaterialTheme.typography.bodySmall,
                            color = SchoolConfig.colorFromHex(selectedSecondaryColor)
                        )
                    }
                }
            }
        }

        // Quick Brand Theme Presets
        Text("Quick Institutional Color Themes", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ThemePresetButton(
                name = "Prakash Blue",
                primaryHex = "#1E40AF",
                secondaryHex = "#F59E0B",
                isSelected = selectedPrimaryColor == "#1E40AF",
                onClick = {
                    selectedPrimaryColor = "#1E40AF"
                    selectedSecondaryColor = "#F59E0B"
                },
                modifier = Modifier.weight(1f)
            )
            ThemePresetButton(
                name = "Oxford Navy",
                primaryHex = "#0F172A",
                secondaryHex = "#D97706",
                isSelected = selectedPrimaryColor == "#0F172A",
                onClick = {
                    selectedPrimaryColor = "#0F172A"
                    selectedSecondaryColor = "#D97706"
                },
                modifier = Modifier.weight(1f)
            )
            ThemePresetButton(
                name = "Emerald Academy",
                primaryHex = "#047857",
                secondaryHex = "#F59E0B",
                isSelected = selectedPrimaryColor == "#047857",
                onClick = {
                    selectedPrimaryColor = "#047857"
                    selectedSecondaryColor = "#F59E0B"
                },
                modifier = Modifier.weight(1f)
            )
            ThemePresetButton(
                name = "Crimson High",
                primaryHex = "#991B1B",
                secondaryHex = "#FBBF24",
                isSelected = selectedPrimaryColor == "#991B1B",
                onClick = {
                    selectedPrimaryColor = "#991B1B"
                    selectedSecondaryColor = "#FBBF24"
                },
                modifier = Modifier.weight(1f)
            )
        }

        // Form Fields
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, SchoolBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = schoolName,
                    onValueChange = { schoolName = it },
                    label = { Text("School Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = shortName,
                        onValueChange = { shortName = it },
                        label = { Text("Short Code") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = academicYear,
                        onValueChange = { academicYear = it },
                        label = { Text("Academic Year") },
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = tagline,
                    onValueChange = { tagline = it },
                    label = { Text("School Tagline / Motto") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Campus Address") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Button(
                    onClick = {
                        val updated = currentConfig.copy(
                            schoolName = schoolName,
                            shortName = shortName,
                            tagline = tagline,
                            academicYear = academicYear,
                            address = address,
                            phone = phone,
                            email = email,
                            website = website,
                            primaryColorHex = selectedPrimaryColor,
                            secondaryColorHex = selectedSecondaryColor
                        )
                        onSave(updated)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("save_branding_button"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Apply & Update Branding", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                }
            }
        }
    }
}

@Composable
private fun ThemePresetButton(
    name: String,
    primaryHex: String,
    secondaryHex: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) SchoolSurfaceSubtle else Color.White,
        border = BorderStroke(
            if (isSelected) 2.dp else 1.dp,
            if (isSelected) SchoolConfig.colorFromHex(primaryHex) else SchoolBorder
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(SchoolConfig.colorFromHex(primaryHex))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(SchoolConfig.colorFromHex(secondaryHex))
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold),
                color = TextPrimary,
                maxLines = 1
            )
        }
    }
}
