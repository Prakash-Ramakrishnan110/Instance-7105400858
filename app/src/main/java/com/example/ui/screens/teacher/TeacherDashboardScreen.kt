package com.example.ui.screens.teacher

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.config.SchoolConfig
import com.example.data.MockSchoolData
import com.example.data.SchoolRepository
import com.example.model.AttendanceStatus
import com.example.model.Student
import com.example.ui.components.QuickActionItem
import com.example.ui.components.SchoolStatCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDashboardScreen(
    schoolConfig: SchoolConfig,
    onNavigateAction: (String) -> Unit
) {
    var selectedSection by remember { mutableStateOf("Overview") } // Overview, Attendance, Homework, Notice
    var showSuccessBanner by remember { mutableStateOf<String?>(null) }

    val teacher = MockSchoolData.teachersList.first() // Mrs. Priya (Mathematics)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("teacher_dashboard_screen")
    ) {
        // Teacher Profile Header
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "MP",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = teacher.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                ),
                                color = Slate900
                            )
                            Text(
                                text = "${teacher.subject} Faculty • Room 204",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 13.sp
                                ),
                                color = Slate500
                            )
                        }
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Teacher Portal",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Classes Assigned
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Today's Classes: ",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = TextMuted
                    )
                    for (cls in teacher.classesAssigned) {
                        Surface(
                            color = SchoolSurfaceSubtle,
                            shape = RoundedCornerShape(6.dp),
                            border = BorderStroke(1.dp, SchoolBorder),
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            Text(
                                text = "Grade $cls",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        // Section Tabs
        PrimaryTabRow(
            selectedTabIndex = when (selectedSection) {
                "Overview" -> 0
                "Attendance" -> 1
                "Homework" -> 2
                "Notice" -> 3
                else -> 0
            },
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            listOf("Overview", "Take Attendance", "Add Homework", "Post Notice").forEachIndexed { index, name ->
                val sectionKey = when (index) {
                    0 -> "Overview"
                    1 -> "Attendance"
                    2 -> "Homework"
                    else -> "Notice"
                }
                Tab(
                    selected = selectedSection == sectionKey,
                    onClick = {
                        selectedSection = sectionKey
                        showSuccessBanner = null
                    },
                    text = {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (selectedSection == sectionKey) FontWeight.Bold else FontWeight.Medium
                            )
                        )
                    }
                )
            }
        }

        if (showSuccessBanner != null) {
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
                        text = showSuccessBanner!!,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        color = Color(0xFF065F46)
                    )
                }
            }
        }

        when (selectedSection) {
            "Overview" -> TeacherOverviewContent(
                onNavigateSection = { selectedSection = it },
                onNavigateAction = onNavigateAction
            )
            "Attendance" -> TeacherAttendanceContent(
                onSaved = { count ->
                    showSuccessBanner = "Attendance recorded for $count students in Grade 10-A!"
                }
            )
            "Homework" -> TeacherAddHomeworkContent(
                onPublished = { title ->
                    showSuccessBanner = "Successfully published homework: $title!"
                }
            )
            "Notice" -> TeacherPostNoticeContent(
                onPosted = { title ->
                    showSuccessBanner = "Class announcement published: $title!"
                }
            )
        }
    }
}

@Composable
fun TeacherOverviewContent(
    onNavigateSection: (String) -> Unit,
    onNavigateAction: (String) -> Unit
) {
    val homeworkList by SchoolRepository.homeworkList.collectAsState()
    val mathHwCount = homeworkList.count { it.subject == "Mathematics" }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Stats
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SchoolStatCard(
                    title = "My Students",
                    value = "104",
                    subtitle = "Across 3 sections",
                    icon = Icons.Default.Groups,
                    iconColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                SchoolStatCard(
                    title = "Class 10-A",
                    value = "94%",
                    subtitle = "Today's Attendance",
                    icon = Icons.Default.CheckCircle,
                    iconColor = StatusSuccess,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateSection("Attendance") }
                )
            }
        }

        // Quick Actions for Teacher
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
                        text = "Teacher Quick Tools",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary,
                        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuickActionItem(
                            title = "Take Attendance",
                            icon = Icons.Default.HowToReg,
                            iconColor = Color(0xFF10B981),
                            onClick = { onNavigateSection("Attendance") },
                            modifier = Modifier.weight(1f)
                        )
                        QuickActionItem(
                            title = "Add Homework",
                            icon = Icons.Default.PostAdd,
                            iconColor = Color(0xFFF59E0B),
                            badgeCount = mathHwCount,
                            onClick = { onNavigateSection("Homework") },
                            modifier = Modifier.weight(1f)
                        )
                        QuickActionItem(
                            title = "Post Notice",
                            icon = Icons.Default.Campaign,
                            iconColor = Color(0xFF3B82F6),
                            onClick = { onNavigateSection("Notice") },
                            modifier = Modifier.weight(1f)
                        )
                        QuickActionItem(
                            title = "Timetable",
                            icon = Icons.Default.Schedule,
                            iconColor = Color(0xFF8B5CF6),
                            onClick = { onNavigateAction("timetable") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Today's Periods to Teach
        item {
            SectionHeader(
                title = "My Teaching Schedule Today",
                subtitle = "Friday • 4 Lecture Periods"
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Period 1: Grade 10-A", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            Text("8:30 AM – 9:20 AM • Room 204 • Quadratic Equations", style = MaterialTheme.typography.bodySmall)
                        }
                        Surface(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(4.dp)) {
                            Text("DONE", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color.White), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, SchoolBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Period 4: Grade 9-B", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            Text("11:20 AM – 12:10 PM • Room 108 • Linear Equations", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        }
                        Surface(color = StatusWarningBg, shape = RoundedCornerShape(4.dp)) {
                            Text("UPCOMING", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = StatusWarning), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TeacherAttendanceContent(
    onSaved: (Int) -> Unit
) {
    val attendanceMap by SchoolRepository.teacherAttendance.collectAsState()
    val students = MockSchoolData.studentsList.filter { it.grade == "10" && it.section == "A" }

    var selectedClass by remember { mutableStateOf("Grade 10-A") }
    var selectedDate by remember { mutableStateOf("Today, Sep 4, 2026") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("teacher_take_attendance"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
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
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Take Roll Call Attendance",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "$selectedClass • $selectedDate",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }

                        Button(
                            onClick = {
                                SchoolRepository.markAllClassAttendance(AttendanceStatus.PRESENT)
                            },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(Icons.Default.DoneAll, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Mark All Present", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Student Attendance Register (${students.size} enrolled)",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
        }

        items(students) { student ->
            val status = attendanceMap[student.id] ?: AttendanceStatus.PRESENT

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, SchoolBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "#${student.rollNo}",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = student.name,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = TextPrimary
                            )
                            Text(
                                text = "Adm: ${student.admissionNo}",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted
                            )
                        }
                    }

                    // 3 Status Pills (P, A, L)
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        AttendancePill(
                            label = "P",
                            isSelected = status == AttendanceStatus.PRESENT,
                            color = StatusSuccess,
                            bgColor = StatusSuccessBg,
                            onClick = {
                                SchoolRepository.updateStudentAttendanceStatus(student.id, AttendanceStatus.PRESENT)
                            }
                        )
                        AttendancePill(
                            label = "A",
                            isSelected = status == AttendanceStatus.ABSENT,
                            color = StatusDanger,
                            bgColor = StatusDangerBg,
                            onClick = {
                                SchoolRepository.updateStudentAttendanceStatus(student.id, AttendanceStatus.ABSENT)
                            }
                        )
                        AttendancePill(
                            label = "L",
                            isSelected = status == AttendanceStatus.LEAVE,
                            color = StatusWarning,
                            bgColor = StatusWarningBg,
                            onClick = {
                                SchoolRepository.updateStudentAttendanceStatus(student.id, AttendanceStatus.LEAVE)
                            }
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val count = SchoolRepository.saveTeacherAttendance(selectedClass, selectedDate)
                    onSaved(count)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("save_attendance_button"),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Submit & Save Attendance", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
            }
        }
    }
}

@Composable
private fun AttendancePill(
    label: String,
    isSelected: Boolean,
    color: Color,
    bgColor: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        shape = CircleShape,
        color = if (isSelected) color else bgColor,
        border = BorderStroke(1.dp, if (isSelected) color else color.copy(alpha = 0.3f))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp
                ),
                color = if (isSelected) Color.White else color
            )
        }
    }
}

@Composable
fun TeacherAddHomeworkContent(
    onPublished: (String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedClass by remember { mutableStateOf("10-A") }
    var dueDate by remember { mutableStateOf("Monday, Sep 7, 2026") }
    var attachedFileName by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("teacher_add_homework"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Create New Homework Assignment",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
            Text(
                text = "Subject: Mathematics • Mrs. Priya",
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
                    // Class selector
                    Text("Select Target Class", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("10-A", "9-B", "8-A").forEach { cls ->
                            val isSel = selectedClass == cls
                            FilterChip(
                                selected = isSel,
                                onClick = { selectedClass = cls },
                                label = { Text("Grade $cls") },
                                shape = RoundedCornerShape(16.dp)
                            )
                        }
                    }

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Assignment Title") },
                        placeholder = { Text("e.g. Exercise 5.3 Word Problems") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("hw_input_title"),
                        shape = RoundedCornerShape(8.dp)
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Instructions & Description") },
                        placeholder = { Text("Detail required problems, steps to show, and submission criteria...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("hw_input_desc"),
                        minLines = 3,
                        shape = RoundedCornerShape(8.dp)
                    )

                    OutlinedTextField(
                        value = dueDate,
                        onValueChange = { dueDate = it },
                        label = { Text("Due Date & Time") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )

                    // Attach File Simulation
                    Surface(
                        color = SchoolSurfaceSubtle,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, SchoolBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                attachedFileName = "Quadratic_Equations_Ex5.3_Worksheet.pdf"
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.AttachFile, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (attachedFileName != null) "Attached: $attachedFileName" else "Tap to attach worksheet or reference PDF",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = if (attachedFileName != null) FontWeight.Bold else FontWeight.Normal
                                ),
                                color = if (attachedFileName != null) MaterialTheme.colorScheme.primary else TextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = {
                            if (title.isNotEmpty()) {
                                SchoolRepository.addHomework(
                                    subject = "Mathematics",
                                    classGrade = selectedClass,
                                    title = title,
                                    description = if (description.isNotEmpty()) description else "Complete questions assigned in textbook.",
                                    dueDate = dueDate,
                                    teacherName = "Mrs. Priya"
                                )
                                onPublished(title)
                                title = ""
                                description = ""
                                attachedFileName = null
                            }
                        },
                        enabled = title.isNotBlank(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("hw_publish_button"),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Publish, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Publish Homework Assignment", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}

@Composable
fun TeacherPostNoticeContent(
    onPosted: (String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var audience by remember { mutableStateOf("Grade 10-A") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Post Classroom Announcement",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
            Text(
                text = "Broadcast reminders and notes directly to students and parents",
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
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Announcement Title") },
                        placeholder = { Text("e.g. Bring Geometry Box Tomorrow") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text("Message Body") },
                        placeholder = { Text("Students in Grade 10-A are required to bring protractor, compass, and ruler for practical geometry class...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4,
                        shape = RoundedCornerShape(8.dp)
                    )

                    Button(
                        onClick = {
                            if (title.isNotEmpty()) {
                                SchoolRepository.postAnnouncement(
                                    title = title,
                                    message = message,
                                    targetAudience = audience,
                                    author = "Mrs. Priya (Maths Teacher)"
                                )
                                onPosted(title)
                                title = ""
                                message = ""
                            }
                        },
                        enabled = title.isNotBlank(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Post Announcement", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}
