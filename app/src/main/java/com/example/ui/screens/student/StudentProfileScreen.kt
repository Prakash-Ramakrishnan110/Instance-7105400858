package com.example.ui.screens.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.model.Student
import com.example.model.UserRole
import com.example.ui.components.ContactSchoolDialog
import com.example.ui.components.PoweredByFooter
import com.example.ui.theme.*

@Composable
fun StudentProfileScreen(
    schoolConfig: SchoolConfig,
    student: Student,
    onSwitchRole: (UserRole) -> Unit,
    onSignOut: () -> Unit
) {
    var showContactDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("student_profile_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Digital Student ID Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // School Header Strip
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = schoolConfig.schoolName.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "STUDENT ID",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )
                    }
                }

                // Student Details Body
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .border(1.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = student.initials,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = student.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp
                            ),
                            color = TextPrimary
                        )
                        Text(
                            text = "Grade ${student.fullGrade} • Roll No: ${student.rollNo}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Adm No: ${student.admissionNo}",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMuted
                        )
                    }
                }

                HorizontalDivider(color = SchoolBorder, modifier = Modifier.padding(horizontal = 16.dp))

                // Metadata Grid
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Parent / Guardian", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                        Text(student.parentName, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                    }
                    Column {
                        Text("Contact Phone", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                        Text(student.parentPhone, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                    }
                    Column {
                        Text("Bus Route", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                        Text("Route #14", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                    }
                }
            }
        }

        // Quick Navigation & Actions List
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, SchoolBorder)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                ProfileOptionItem(
                    title = "Contact School Administration",
                    subtitle = "Direct phone, email, and campus address",
                    icon = Icons.Default.Phone,
                    onClick = { showContactDialog = true }
                )
                HorizontalDivider(color = SchoolBorder)
                ProfileOptionItem(
                    title = "Switch Demo Persona",
                    subtitle = "Preview Parent, Teacher, or Admin experience",
                    icon = Icons.Default.SwapHoriz,
                    onClick = { onSwitchRole(UserRole.PARENT) }
                )
                HorizontalDivider(color = SchoolBorder)
                ProfileOptionItem(
                    title = "About Custom Branded School Apps",
                    subtitle = schoolConfig.poweredBy,
                    icon = Icons.Default.Info,
                    onClick = { showAboutDialog = true }
                )
                HorizontalDivider(color = SchoolBorder)
                ProfileOptionItem(
                    title = "Sign Out",
                    subtitle = "Return to login screen",
                    icon = Icons.Default.Logout,
                    iconTint = StatusDanger,
                    onClick = onSignOut
                )
            }
        }

        PoweredByFooter(poweredBy = schoolConfig.poweredBy)
    }

    if (showContactDialog) {
        ContactSchoolDialog(schoolConfig = schoolConfig, onDismiss = { showContactDialog = false })
    }

    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            title = {
                Text(
                    text = "Your School. Your Own App.",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Every educational institution can receive its own customized, fully branded Android application.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "• Custom School Name & Logo\n• Institutional Color Palette\n• Dedicated Play Store Listing\n• Real-Time Homework & Attendance\n• Teacher Portal & Admin Controls",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = schoolConfig.poweredBy,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showAboutDialog = false }, shape = RoundedCornerShape(8.dp)) {
                    Text("Got It")
                }
            }
        )
    }
}

@Composable
private fun ProfileOptionItem(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color = TextSecondary,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                color = if (iconTint == StatusDanger) StatusDanger else TextPrimary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                color = TextSecondary
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(20.dp)
        )
    }
}
