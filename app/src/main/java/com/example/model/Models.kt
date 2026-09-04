package com.example.model

enum class UserRole(val displayName: String, val badge: String) {
    STUDENT("Student", "Arjun • 10-A"),
    PARENT("Parent", "Mr. Kumar"),
    TEACHER("Teacher", "Mrs. Priya"),
    ADMIN("Admin", "School Admin")
}

data class Student(
    val id: String,
    val name: String,
    val grade: String,
    val section: String,
    val rollNo: Int,
    val parentName: String,
    val parentPhone: String,
    val admissionNo: String,
    val attendancePct: Int,
    val status: String = "Active"
) {
    val fullGrade: String get() = "$grade-$section"
    val initials: String
        get() = name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString("")
}

data class Teacher(
    val id: String,
    val name: String,
    val subject: String,
    val classes: List<String>,
    val email: String,
    val phone: String,
    val room: String
) {
    val classesAssigned: List<String> get() = classes
}

data class Homework(
    val id: String,
    val subject: String,
    val classGrade: String,
    val title: String,
    val description: String,
    val dueDate: String,
    val assignedDate: String,
    val teacherName: String,
    val status: String = "Pending", // "Pending", "Submitted", "Completed"
    val submissionAttachment: String? = null,
    val submissionNote: String? = null
)

enum class AttendanceStatus(val label: String) {
    PRESENT("Present"),
    ABSENT("Absent"),
    LEAVE("Leave"),
    HOLIDAY("Holiday")
}

data class AttendanceDay(
    val dayNumber: Int,
    val dayOfWeek: String,
    val status: AttendanceStatus,
    val note: String = ""
)

data class TimetableSlot(
    val id: String,
    val period: Int,
    val time: String,
    val subject: String,
    val teacher: String,
    val room: String,
    val isCurrentOrNext: Boolean = false
)

enum class StudyMaterialType(val label: String, val ext: String) {
    PDF("PDF Document", "pdf"),
    VIDEO("Video Lecture", "mp4"),
    WORKSHEET("Practice Worksheet", "pdf"),
    REFERENCE("Reference Material", "epub")
}

data class StudyMaterial(
    val id: String,
    val subject: String,
    val title: String,
    val type: StudyMaterialType,
    val fileName: String,
    val fileSize: String,
    val downloadCount: Int,
    val uploadDate: String
)

data class Exam(
    val id: String,
    val name: String,
    val term: String,
    val subject: String,
    val date: String,
    val time: String,
    val room: String,
    val maxMarks: Int = 100
)

data class ExamResult(
    val subject: String,
    val marks: Int,
    val maxMarks: Int = 100,
    val grade: String,
    val remarks: String
)

data class Announcement(
    val id: String,
    val title: String,
    val date: String,
    val shortDesc: String,
    val fullDesc: String,
    val targetAudience: String, // "All", "Students", "Parents", "Teachers"
    val author: String,
    val isUrgent: Boolean = false
)

data class EventItem(
    val id: String,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val description: String,
    val category: String
)

enum class NotificationCategory(val title: String) {
    SCHOOL("School"),
    HOMEWORK("Homework"),
    EXAM("Exam"),
    ATTENDANCE("Attendance"),
    EVENT("Event")
}

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val category: NotificationCategory,
    val isRead: Boolean = false
)
