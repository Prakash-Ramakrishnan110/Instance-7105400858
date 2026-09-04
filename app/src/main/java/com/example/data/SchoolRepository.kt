package com.example.data

import com.example.config.SchoolConfig
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

object SchoolRepository {

    private val _schoolConfig = MutableStateFlow(SchoolConfig())
    val schoolConfig: StateFlow<SchoolConfig> = _schoolConfig.asStateFlow()

    private val _currentRole = MutableStateFlow(UserRole.STUDENT)
    val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

    private val _currentStudent = MutableStateFlow(MockSchoolData.studentsList.first())
    val currentStudent: StateFlow<Student> = _currentStudent.asStateFlow()

    private val _students = MutableStateFlow(MockSchoolData.studentsList)
    val students: StateFlow<List<Student>> = _students.asStateFlow()

    private val _teachers = MutableStateFlow(MockSchoolData.teachersList)
    val teachers: StateFlow<List<Teacher>> = _teachers.asStateFlow()

    private val _homeworkList = MutableStateFlow(MockSchoolData.initialHomework)
    val homeworkList: StateFlow<List<Homework>> = _homeworkList.asStateFlow()

    private val _announcements = MutableStateFlow(MockSchoolData.initialAnnouncements)
    val announcements: StateFlow<List<Announcement>> = _announcements.asStateFlow()

    private val _notifications = MutableStateFlow(MockSchoolData.initialNotifications)
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _events = MutableStateFlow(MockSchoolData.initialEvents)
    val events: StateFlow<List<EventItem>> = _events.asStateFlow()

    // Teacher Attendance Map for 10-A (studentId -> Status)
    private val _teacherAttendance = MutableStateFlow<Map<String, AttendanceStatus>>(
        MockSchoolData.studentsList.filter { it.grade == "10" && it.section == "A" }
            .associate { it.id to (if (it.name == "Priya S") AttendanceStatus.ABSENT else AttendanceStatus.PRESENT) }
    )
    val teacherAttendance: StateFlow<Map<String, AttendanceStatus>> = _teacherAttendance.asStateFlow()

    fun switchRole(newRole: UserRole) {
        _currentRole.value = newRole
    }

    fun updateSchoolConfig(newConfig: SchoolConfig) {
        _schoolConfig.value = newConfig
    }

    fun markHomeworkCompleted(homeworkId: String) {
        _homeworkList.value = _homeworkList.value.map { hw ->
            if (hw.id == homeworkId) hw.copy(status = "Completed") else hw
        }
    }

    fun submitHomework(homeworkId: String, note: String, attachmentName: String = "submission_document.pdf") {
        _homeworkList.value = _homeworkList.value.map { hw ->
            if (hw.id == homeworkId) {
                hw.copy(
                    status = "Submitted",
                    submissionNote = note,
                    submissionAttachment = attachmentName
                )
            } else hw
        }
    }

    fun addHomework(
        subject: String,
        classGrade: String,
        title: String,
        description: String,
        dueDate: String,
        teacherName: String
    ) {
        val newHw = Homework(
            id = "hw_${UUID.randomUUID().toString().take(6)}",
            subject = subject,
            classGrade = classGrade,
            title = title,
            description = description,
            dueDate = dueDate,
            assignedDate = "Just now",
            teacherName = teacherName,
            status = "Pending"
        )
        _homeworkList.value = listOf(newHw) + _homeworkList.value

        // Post auto notification
        sendNotification(
            title = "New $subject Homework",
            message = "$title has been published for $classGrade.",
            category = NotificationCategory.HOMEWORK
        )
    }

    fun updateStudentAttendanceStatus(studentId: String, status: AttendanceStatus) {
        _teacherAttendance.value = _teacherAttendance.value.toMutableMap().apply {
            put(studentId, status)
        }
    }

    fun markAllClassAttendance(status: AttendanceStatus) {
        val currentKeys = _teacherAttendance.value.keys
        _teacherAttendance.value = currentKeys.associateWith { status }
    }

    fun saveTeacherAttendance(classGrade: String, date: String): Int {
        val count = _teacherAttendance.value.size
        // Add attendance notification
        sendNotification(
            title = "Attendance Updated",
            message = "Attendance for $classGrade on $date successfully submitted by Mrs. Priya.",
            category = NotificationCategory.ATTENDANCE
        )
        return count
    }

    fun postAnnouncement(
        title: String,
        message: String,
        targetAudience: String,
        author: String = "School Administration"
    ) {
        val newAnnouncement = Announcement(
            id = "a_${UUID.randomUUID().toString().take(6)}",
            title = title,
            date = "Today",
            shortDesc = if (message.length > 80) message.take(77) + "..." else message,
            fullDesc = message,
            targetAudience = targetAudience,
            author = author,
            isUrgent = false
        )
        _announcements.value = listOf(newAnnouncement) + _announcements.value

        sendNotification(
            title = title,
            message = newAnnouncement.shortDesc,
            category = NotificationCategory.SCHOOL
        )
    }

    fun sendNotification(
        title: String,
        message: String,
        category: NotificationCategory
    ) {
        val newNotif = NotificationItem(
            id = "n_${UUID.randomUUID().toString().take(6)}",
            title = title,
            message = message,
            timestamp = "Just now",
            category = category,
            isRead = false
        )
        _notifications.value = listOf(newNotif) + _notifications.value
    }

    fun markNotificationRead(id: String) {
        _notifications.value = _notifications.value.map {
            if (it.id == id) it.copy(isRead = true) else it
        }
    }

    fun markAllNotificationsRead() {
        _notifications.value = _notifications.value.map { it.copy(isRead = true) }
    }
}
