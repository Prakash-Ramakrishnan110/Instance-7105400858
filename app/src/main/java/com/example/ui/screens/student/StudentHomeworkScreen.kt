package com.example.ui.screens.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SchoolRepository
import com.example.model.Homework
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun StudentHomeworkScreen(
    onNavigateBack: (() -> Unit)? = null
) {
    val homeworkList by SchoolRepository.homeworkList.collectAsState()

    var selectedFilter by remember { mutableStateOf("All") } // "All", "Pending", "Submitted", "Completed"
    var selectedSubject by remember { mutableStateOf("All") }

    var homeworkToSubmit by remember { mutableStateOf<Homework?>(null) }
    var homeworkForDetails by remember { mutableStateOf<Homework?>(null) }
    var showSuccessMessage by remember { mutableStateOf<String?>(null) }

    val subjects = listOf("All", "Mathematics", "Science", "English", "Computer Science", "Social Science", "Tamil")

    val filteredList = homeworkList.filter { hw ->
        val matchesStatus = when (selectedFilter) {
            "Pending" -> hw.status == "Pending"
            "Submitted" -> hw.status == "Submitted"
            "Completed" -> hw.status == "Completed"
            else -> true
        }
        val matchesSubject = selectedSubject == "All" || hw.subject.equals(selectedSubject, ignoreCase = true)
        matchesStatus && matchesSubject
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("student_homework_screen")
    ) {
        // Status Filter Chips Row
        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 1.dp
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("All", "Pending", "Submitted", "Completed").forEach { filter ->
                        val isSelected = selectedFilter == filter
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedFilter = filter },
                            label = {
                                Text(
                                    text = filter,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                )
                            },
                            shape = RoundedCornerShape(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Subject Horizontal Filter
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(subjects) { subj ->
                        val isSelected = selectedSubject == subj
                        SuggestionChip(
                            onClick = { selectedSubject = subj },
                            label = {
                                Text(
                                    text = subj,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 11.sp
                                    )
                                )
                            },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                            ),
                            border = SuggestionChipDefaults.suggestionChipBorder(
                                enabled = true,
                                borderColor = if (isSelected) MaterialTheme.colorScheme.primary else SchoolBorder
                            )
                        )
                    }
                }
            }
        }

        if (showSuccessMessage != null) {
            Surface(
                color = StatusSuccessBg,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
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
                        text = showSuccessMessage ?: "",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        color = Color(0xFF065F46)
                    )
                }
            }
        }

        // Homework List
        if (filteredList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Outlined.AssignmentTurnedIn,
                        contentDescription = null,
                        modifier = Modifier.size(56.dp),
                        tint = TextMuted
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No Homework Found",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary
                    )
                    Text(
                        text = "Great job! All caught up in this category.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredList) { hw ->
                    HomeworkItemDetailedCard(
                        homework = hw,
                        onViewDetails = { homeworkForDetails = hw },
                        onUploadSubmission = { homeworkToSubmit = hw },
                        onMarkCompleted = {
                            SchoolRepository.markHomeworkCompleted(hw.id)
                            showSuccessMessage = "Marked ${hw.title} as Completed!"
                        }
                    )
                }
            }
        }
    }

    // Details Modal
    if (homeworkForDetails != null) {
        HomeworkDetailsDialog(
            homework = homeworkForDetails!!,
            onDismiss = { homeworkForDetails = null },
            onMarkCompleted = {
                SchoolRepository.markHomeworkCompleted(homeworkForDetails!!.id)
                homeworkForDetails = null
                showSuccessMessage = "Task marked completed!"
            }
        )
    }

    // Submission Dialog
    if (homeworkToSubmit != null) {
        HomeworkSubmitDialog(
            homework = homeworkToSubmit!!,
            onDismiss = { homeworkToSubmit = null },
            onSubmit = { note, fileName ->
                SchoolRepository.submitHomework(homeworkToSubmit!!.id, note, fileName)
                homeworkToSubmit = null
                showSuccessMessage = "Homework submitted successfully!"
            }
        )
    }
}

@Composable
fun HomeworkItemDetailedCard(
    homework: Homework,
    onViewDetails: () -> Unit,
    onUploadSubmission: () -> Unit,
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
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = homework.subject,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "• Grade ${homework.classGrade}",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }

                StatusBadge(status = homework.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = homework.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = homework.description,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = TextSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Due: ${homework.dueDate}",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                        color = TextPrimary
                    )
                }

                Text(
                    text = "By ${homework.teacherName}",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted
                )
            }

            if (homework.submissionAttachment != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = SchoolSurfaceSubtle,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AttachFile,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = StatusSuccess
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Attached: ${homework.submissionAttachment}",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                            color = TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = SchoolBorder)
            Spacer(modifier = Modifier.height(8.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onViewDetails,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("View Details")
                }

                if (homework.status == "Pending") {
                    Spacer(modifier = Modifier.width(6.dp))
                    OutlinedButton(
                        onClick = onUploadSubmission,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Upload")
                    }

                    Spacer(modifier = Modifier.width(6.dp))
                    Button(
                        onClick = onMarkCompleted,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Mark Completed")
                    }
                }
            }
        }
    }
}

@Composable
fun HomeworkSubmitDialog(
    homework: Homework,
    onDismiss: () -> Unit,
    onSubmit: (note: String, attachment: String) -> Unit
) {
    var studentNote by remember { mutableStateOf("") }
    var selectedFile by remember { mutableStateOf("${homework.subject}_Assignment_Arjun.pdf") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Upload Submission",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "${homework.subject}: ${homework.title}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary
                )

                Surface(
                    color = SchoolSurfaceSubtle,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, SchoolBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.InsertDriveFile,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = selectedFile,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "PDF Document • 2.4 MB (Ready to upload)",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = studentNote,
                    onValueChange = { studentNote = it },
                    label = { Text("Note for teacher (optional)") },
                    placeholder = { Text("e.g. Please find exercise 5.2 working steps attached...") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    shape = RoundedCornerShape(8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSubmit(studentNote, selectedFile) },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Submit Homework")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
