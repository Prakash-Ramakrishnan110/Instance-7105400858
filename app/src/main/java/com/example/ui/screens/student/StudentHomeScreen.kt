package com.example.ui.screens.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.config.SchoolConfig
import com.example.data.MockSchoolData
import com.example.data.SchoolRepository
import com.example.model.Homework
import com.example.model.Student
import com.example.model.TimetableSlot
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun StudentHomeScreen(
    schoolConfig: SchoolConfig,
    student: Student,
    onNavigateAction: (String) -> Unit
) {
    val homeworkList by SchoolRepository.homeworkList.collectAsState()
    val announcements by SchoolRepository.announcements.collectAsState()
    val notifications by SchoolRepository.notifications.collectAsState()

    val pendingCount = homeworkList.count { it.status == "Pending" }
    val unreadNotifs = notifications.count { !it.isRead }

    var selectedHomeworkForDetails by remember { mutableStateOf<Homework?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("student_home_screen"),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Welcome Section (Professional Polish)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "Good Morning, Arjun 👋",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    color = Slate900
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Grade ${student.fullGrade} • Roll No: ${student.rollNo}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.5.sp
                    ),
                    color = Slate500
                )
            }
        }

        // Stats Row (2 Columns matching Design Theme)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SchoolStatCard(
                    title = "Attendance",
                    value = "${student.attendancePct}%",
                    subtitle = "184 / 200 Days",
                    icon = Icons.Default.Check,
                    iconColor = StatusSuccess,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("stat_attendance"),
                    onClick = { onNavigateAction("attendance") }
                )

                SchoolStatCard(
                    title = "Homework",
                    value = if (pendingCount < 10) "0$pendingCount" else "$pendingCount",
                    subtitle = "Pending tasks",
                    icon = Icons.Default.Assignment,
                    iconColor = StatusWarning,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("stat_homework"),
                    onClick = { onNavigateAction("homework") }
                )
            }
        }

        // Featured Class / Next Up (Professional Polish Banner)
        item {
            Spacer(modifier = Modifier.height(14.dp))
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                FeaturedClassCard(
                    subject = "Computer Science",
                    topic = "Introduction to Flutter Widgets",
                    time = "11:20 AM — 12:10 PM",
                    teacherName = "Mr. Sivakumar",
                    teacherInitials = "MS",
                    onViewNotesClick = { onNavigateAction("materials") },
                    backgroundColor = schoolConfig.primaryColor
                )
            }
        }

        // Quick Actions Grid (4-Columns matching Design HTML)
        item {
            Spacer(modifier = Modifier.height(14.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickActionItem(
                        title = "Homework",
                        icon = Icons.Default.Assignment,
                        iconColor = Color(0xFF2563EB),
                        badgeCount = pendingCount,
                        onClick = { onNavigateAction("homework") },
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionItem(
                        title = "Timetable",
                        icon = Icons.Default.CalendarMonth,
                        iconColor = Color(0xFFF97316),
                        onClick = { onNavigateAction("timetable") },
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionItem(
                        title = "Attendance",
                        icon = Icons.Default.CheckCircle,
                        iconColor = Color(0xFF10B981),
                        onClick = { onNavigateAction("attendance") },
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionItem(
                        title = "Study Mat",
                        icon = Icons.Default.MenuBook,
                        iconColor = Color(0xFF8B5CF6),
                        onClick = { onNavigateAction("materials") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickActionItem(
                        title = "Results",
                        icon = Icons.Default.BarChart,
                        iconColor = Color(0xFFEF4444),
                        onClick = { onNavigateAction("results") },
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionItem(
                        title = "Updates",
                        icon = Icons.Default.Campaign,
                        iconColor = Color(0xFF0891B2),
                        badgeCount = unreadNotifs,
                        onClick = { onNavigateAction("updates") },
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionItem(
                        title = "Events",
                        icon = Icons.Default.LocationOn,
                        iconColor = Color(0xFFF43F5E),
                        onClick = { onNavigateAction("events") },
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionItem(
                        title = "More",
                        icon = Icons.Default.MoreHoriz,
                        iconColor = Color(0xFF94A3B8),
                        onClick = { onNavigateAction("id_card") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Today's Timetable
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(
                title = "Today's Schedule",
                subtitle = "Grade 10-A • 6 Periods",
                actionText = "Full Week",
                onActionClick = { onNavigateAction("timetable") }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MockSchoolData.todayTimetable.take(4).forEach { slot ->
                    TimetableSlotCard(slot = slot)
                }
            }
        }

        // Recent Homework
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(
                title = "Active Homework",
                subtitle = "Tasks requiring your attention",
                actionText = "View All ($pendingCount)",
                onActionClick = { onNavigateAction("homework") }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                homeworkList.take(3).forEach { hw ->
                    HomeworkCard(
                        homework = hw,
                        onViewDetails = { selectedHomeworkForDetails = hw },
                        onMarkCompleted = { SchoolRepository.markHomeworkCompleted(hw.id) }
                    )
                }
            }
        }

        // Urgent Notice Banner
        item {
            val urgentNotice = announcements.firstOrNull { it.isUrgent }
            if (urgentNotice != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    color = Color(0xFFFFFBEB),
                    border = BorderStroke(1.dp, Color(0xFFFDE68A))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF59E0B)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Campaign,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = urgentNotice.title,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF92400E)
                            )
                            Text(
                                text = urgentNotice.shortDesc,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF78350F),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }

    if (selectedHomeworkForDetails != null) {
        HomeworkDetailsDialog(
            homework = selectedHomeworkForDetails!!,
            onDismiss = { selectedHomeworkForDetails = null },
            onMarkCompleted = {
                SchoolRepository.markHomeworkCompleted(selectedHomeworkForDetails!!.id)
                selectedHomeworkForDetails = null
            }
        )
    }
}

@Composable
fun TimetableSlotCard(slot: TimetableSlot) {
    val isHighlighted = slot.isCurrentOrNext
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isHighlighted) MaterialTheme.colorScheme.primaryContainer else Color.White
        ),
        border = BorderStroke(
            1.dp,
            if (isHighlighted) MaterialTheme.colorScheme.primary else SchoolBorder
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Period number pill
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            if (isHighlighted) MaterialTheme.colorScheme.primary else SchoolSurfaceSubtle
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "P${slot.period}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        ),
                        color = if (isHighlighted) Color.White else TextSecondary
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = slot.subject,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            ),
                            color = TextPrimary
                        )
                        if (isHighlighted) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "NEXT CLASS",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    ),
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = "${slot.teacher} • ${slot.room}",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = TextSecondary
                    )
                }
            }

            Text(
                text = slot.time,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp
                ),
                color = if (isHighlighted) MaterialTheme.colorScheme.primary else TextMuted
            )
        }
    }
}

@Composable
fun HomeworkCard(
    homework: Homework,
    onViewDetails: () -> Unit,
    onMarkCompleted: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, SchoolBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(
                                when (homework.subject) {
                                    "Mathematics" -> Color(0xFF3B82F6)
                                    "Science" -> Color(0xFF10B981)
                                    "English" -> Color(0xFF8B5CF6)
                                    "Computer Science" -> Color(0xFF06B6D4)
                                    else -> Color(0xFFF59E0B)
                                }
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = homework.subject,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        ),
                        color = TextSecondary
                    )
                }

                StatusBadge(status = homework.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = homework.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                ),
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = homework.description,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = TextMuted
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Due: ${homework.dueDate}",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    TextButton(
                        onClick = onViewDetails,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text("Details", style = MaterialTheme.typography.labelMedium)
                    }

                    if (homework.status == "Pending") {
                        FilledTonalButton(
                            onClick = onMarkCompleted,
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Done", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeworkDetailsDialog(
    homework: Homework,
    onDismiss: () -> Unit,
    onMarkCompleted: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = homework.subject,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    StatusBadge(status = homework.status)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = homework.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = homework.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary
                )

                HorizontalDivider()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = TextSecondary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Assigned by: ${homework.teacherName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = TextSecondary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Due Date: ${homework.dueDate}",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                        color = TextPrimary
                    )
                }

                if (homework.submissionAttachment != null) {
                    Surface(
                        color = StatusSuccessBg,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, StatusSuccess.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AttachFile,
                                contentDescription = null,
                                tint = StatusSuccess,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Submitted File:",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = StatusSuccess
                                )
                                Text(
                                    text = homework.submissionAttachment,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = TextPrimary
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (homework.status == "Pending") {
                Button(
                    onClick = onMarkCompleted,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Mark as Completed")
                }
            } else {
                TextButton(onClick = onDismiss) {
                    Text("Close")
                }
            }
        },
        dismissButton = {
            if (homework.status == "Pending") {
                TextButton(onClick = onDismiss) {
                    Text("Close")
                }
            }
        }
    )
}
