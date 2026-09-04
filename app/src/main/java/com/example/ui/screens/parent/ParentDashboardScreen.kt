package com.example.ui.screens.parent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.model.Student
import com.example.ui.components.*
import com.example.ui.screens.student.HomeworkCard
import com.example.ui.screens.student.TimetableSlotCard
import com.example.ui.theme.*

@Composable
fun ParentDashboardScreen(
    schoolConfig: SchoolConfig,
    student: Student,
    onNavigateAction: (String) -> Unit
) {
    val homeworkList by SchoolRepository.homeworkList.collectAsState()
    val pendingHwCount = homeworkList.count { it.status == "Pending" }

    var showContactDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("parent_dashboard_screen"),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Reassuring Parent Header
        item {
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
                                text = "Good Morning, Mr. Kumar 👋",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                ),
                                color = Slate900
                            )
                            Text(
                                text = "Parent of Arjun Kumar • Term 1 Overview",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 13.sp
                                ),
                                color = Slate500
                            )
                        }

                        IconButton(
                            onClick = { showContactDialog = true },
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Contact School",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Child Switcher Badge Card
                    Surface(
                        color = Slate50,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Slate200),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = student.initials,
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${student.name} (Grade ${student.fullGrade})",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    ),
                                    color = Slate900
                                )
                                Text(
                                    text = "Roll No: ${student.rollNo} • Adm No: ${student.admissionNo}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 11.sp
                                    ),
                                    color = Slate500
                                )
                            }

                            Surface(
                                color = StatusSuccessBg,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "Active Student",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp,
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

        // Child Academic Stats (4 Cards)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SchoolStatCard(
                        title = "Attendance",
                        value = "${student.attendancePct}%",
                        subtitle = "184 Present / 16 Absent",
                        icon = Icons.Default.CheckCircle,
                        iconColor = StatusSuccess,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("parent_stat_attendance"),
                        onClick = { onNavigateAction("attendance") }
                    )

                    SchoolStatCard(
                        title = "Pending Homework",
                        value = "$pendingHwCount Tasks",
                        subtitle = "Due this week",
                        icon = Icons.Default.Assignment,
                        iconColor = StatusWarning,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("parent_stat_homework"),
                        onClick = { onNavigateAction("homework") }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SchoolStatCard(
                        title = "Next Exam",
                        value = "Mathematics",
                        subtitle = "15 Sep • Quarterly",
                        icon = Icons.Default.MenuBook,
                        iconColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("parent_stat_exam"),
                        onClick = { onNavigateAction("exams") }
                    )

                    SchoolStatCard(
                        title = "Latest Results",
                        value = "91% (A+)",
                        subtitle = "Term 1 Assessment",
                        icon = Icons.Default.Stars,
                        iconColor = Color(0xFF10B981),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("parent_stat_results"),
                        onClick = { onNavigateAction("results") }
                    )
                }
            }
        }

        // Quick Actions
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, SchoolBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Parent Services & Monitoring",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary,
                        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuickActionItem(
                            title = "Attendance",
                            icon = Icons.Default.EventAvailable,
                            iconColor = Color(0xFF10B981),
                            onClick = { onNavigateAction("attendance") },
                            modifier = Modifier.weight(1f)
                        )
                        QuickActionItem(
                            title = "Homework",
                            icon = Icons.Default.Assignment,
                            iconColor = Color(0xFFF59E0B),
                            badgeCount = pendingHwCount,
                            onClick = { onNavigateAction("homework") },
                            modifier = Modifier.weight(1f)
                        )
                        QuickActionItem(
                            title = "Timetable",
                            icon = Icons.Default.Schedule,
                            iconColor = Color(0xFF3B82F6),
                            onClick = { onNavigateAction("timetable") },
                            modifier = Modifier.weight(1f)
                        )
                        QuickActionItem(
                            title = "Results",
                            icon = Icons.Default.Stars,
                            iconColor = Color(0xFFF97316),
                            onClick = { onNavigateAction("results") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuickActionItem(
                            title = "Exams",
                            icon = Icons.Default.Description,
                            iconColor = Color(0xFFEC4899),
                            onClick = { onNavigateAction("exams") },
                            modifier = Modifier.weight(1f)
                        )
                        QuickActionItem(
                            title = "Announcements",
                            icon = Icons.Default.Campaign,
                            iconColor = Color(0xFF06B6D4),
                            onClick = { onNavigateAction("updates") },
                            modifier = Modifier.weight(1f)
                        )
                        QuickActionItem(
                            title = "Events",
                            icon = Icons.Default.Festival,
                            iconColor = Color(0xFF8B5CF6),
                            onClick = { onNavigateAction("events") },
                            modifier = Modifier.weight(1f)
                        )
                        QuickActionItem(
                            title = "Contact School",
                            icon = Icons.Default.PhoneInTalk,
                            iconColor = Color(0xFF0D9488),
                            onClick = { showContactDialog = true },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Today's Timetable for Child
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(
                title = "Arjun's Schedule Today",
                subtitle = "Grade 10-A",
                actionText = "Full Week",
                onActionClick = { onNavigateAction("timetable") }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MockSchoolData.todayTimetable.take(3).forEach { slot ->
                    TimetableSlotCard(slot = slot)
                }
            }
        }

        // Recent Homework list
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(
                title = "Assigned Homework",
                subtitle = "Keep track of upcoming deadlines",
                actionText = "View All",
                onActionClick = { onNavigateAction("homework") }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                homeworkList.take(2).forEach { hw ->
                    HomeworkCard(
                        homework = hw,
                        onViewDetails = { onNavigateAction("homework") },
                        onMarkCompleted = { SchoolRepository.markHomeworkCompleted(hw.id) }
                    )
                }
            }
        }
    }

    if (showContactDialog) {
        ContactSchoolDialog(schoolConfig = schoolConfig, onDismiss = { showContactDialog = false })
    }
}
