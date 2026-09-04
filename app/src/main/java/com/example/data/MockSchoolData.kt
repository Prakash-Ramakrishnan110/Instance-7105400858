package com.example.data

import com.example.model.*

object MockSchoolData {

    val studentsList = listOf(
        Student("s1", "Arjun Kumar", "10", "A", 24, "R. Kumar", "+91 98401 22334", "PIS-2024-1024", 92),
        Student("s2", "Rahul Kumar", "10", "A", 12, "S. Kumar", "+91 98402 33445", "PIS-2024-1012", 96),
        Student("s3", "Priya S", "10", "A", 18, "M. Shanmugam", "+91 98403 44556", "PIS-2024-1018", 88),
        Student("s4", "Karthik R", "10", "A", 8, "V. Radhakrishnan", "+91 98404 55667", "PIS-2024-1008", 94),
        Student("s5", "Deepa M", "10", "A", 5, "N. Murugan", "+91 98405 66778", "PIS-2024-1005", 98),
        Student("s6", "Vignesh S", "10", "A", 29, "P. Subramaniam", "+91 98406 77889", "PIS-2024-1029", 90),
        Student("s7", "Ananya R", "10", "A", 3, "T. Ramesh", "+91 98407 88990", "PIS-2024-1003", 95),
        Student("s8", "Divya K", "10", "A", 6, "K. Karunakaran", "+91 98408 99001", "PIS-2024-1006", 91),
        Student("s9", "Sai Ganesh", "10", "A", 21, "G. Balaji", "+91 98409 00112", "PIS-2024-1021", 89),
        Student("s10", "Meera N", "10", "A", 15, "A. Narayanan", "+91 98410 11223", "PIS-2024-1015", 97),
        Student("s11", "Harish B", "10", "B", 10, "B. Baskaran", "+91 98411 22334", "PIS-2024-1050", 93),
        Student("s12", "Pooja V", "10", "B", 17, "V. Vijay", "+91 98412 33445", "PIS-2024-1057", 89),
        Student("s13", "Sanjay K", "10", "B", 25, "K. Krishnan", "+91 98413 44556", "PIS-2024-1065", 86),
        Student("s14", "Rithanya S", "10", "B", 22, "S. Sundar", "+91 98414 55667", "PIS-2024-1062", 94),
        Student("s15", "Aditya P", "9", "A", 4, "P. Prakash", "+91 98415 66778", "PIS-2024-0904", 95),
        Student("s16", "Swetha M", "9", "A", 28, "M. Mani", "+91 98416 77889", "PIS-2024-0928", 91),
        Student("s17", "Naveen R", "9", "B", 14, "R. Rajan", "+91 98417 88990", "PIS-2024-0954", 88),
        Student("s18", "Keerthi J", "9", "B", 9, "J. Jayakumar", "+91 98418 99001", "PIS-2024-0949", 96),
        Student("s19", "Varun T", "8", "A", 30, "T. Thangaraj", "+91 98419 00112", "PIS-2024-0830", 92),
        Student("s20", "Sneha L", "8", "A", 26, "L. Loganathan", "+91 98420 11223", "PIS-2024-0826", 97),
        Student("s21", "Siddharth M", "8", "B", 19, "M. Mohan", "+91 98421 22334", "PIS-2024-0859", 90),
        Student("s22", "Pavithra B", "8", "B", 16, "B. Babu", "+91 98422 33445", "PIS-2024-0856", 93)
    )

    val teachersList = listOf(
        Teacher("t1", "Mrs. Priya", "Mathematics", listOf("10-A", "9-B", "8-A"), "priya.math@prakashschool.edu.in", "+91 98401 11001", "Room 204"),
        Teacher("t2", "Mr. Raman", "Science", listOf("10-A", "10-B", "9-A"), "raman.sci@prakashschool.edu.in", "+91 98401 11002", "Physics Lab"),
        Teacher("t3", "Mrs. Sarah", "English", listOf("10-A", "10-B", "8-B"), "sarah.eng@prakashschool.edu.in", "+91 98401 11003", "Room 202"),
        Teacher("t4", "Mr. Anand", "Computer Science", listOf("10-A", "9-A", "8-A"), "anand.cs@prakashschool.edu.in", "+91 98401 11004", "IT Lab 1"),
        Teacher("t5", "Mrs. Lakshmi", "Social Science", listOf("10-A", "9-B", "8-A"), "lakshmi.ss@prakashschool.edu.in", "+91 98401 11005", "Room 208"),
        Teacher("t6", "Mr. Selvam", "Tamil", listOf("10-A", "9-A", "8-B"), "selvam.tam@prakashschool.edu.in", "+91 98401 11006", "Room 201")
    )

    val todayTimetable = listOf(
        TimetableSlot("tt1", 1, "8:30 AM – 9:20 AM", "Mathematics", "Mrs. Priya", "Room 204", isCurrentOrNext = false),
        TimetableSlot("tt2", 2, "9:20 AM – 10:10 AM", "Science", "Mr. Raman", "Physics Lab", isCurrentOrNext = true),
        TimetableSlot("tt3", 3, "10:10 AM – 11:00 AM", "English", "Mrs. Sarah", "Room 202", isCurrentOrNext = false),
        TimetableSlot("tt4", 4, "11:20 AM – 12:10 PM", "Computer Science", "Mr. Anand", "IT Lab 1", isCurrentOrNext = false),
        TimetableSlot("tt5", 5, "12:10 PM – 1:00 PM", "Social Science", "Mrs. Lakshmi", "Room 208", isCurrentOrNext = false),
        TimetableSlot("tt6", 6, "1:30 PM – 2:20 PM", "Tamil", "Mr. Selvam", "Room 201", isCurrentOrNext = false)
    )

    val initialHomework = listOf(
        Homework(
            id = "hw1",
            subject = "Mathematics",
            classGrade = "10-A",
            title = "Complete Exercise 5.2",
            description = "Solve problems 1 to 15 from Quadratic Equations. Show working steps clearly in homework notebook.",
            dueDate = "Tomorrow, 8:30 AM",
            assignedDate = "Yesterday",
            teacherName = "Mrs. Priya",
            status = "Pending"
        ),
        Homework(
            id = "hw2",
            subject = "Science",
            classGrade = "10-A",
            title = "Prepare Chapter 4 Notes",
            description = "Carbon and Its Compounds: Summarize covalent bonding, allotropes of carbon, and write reactions for combustion.",
            dueDate = "Friday, 9:20 AM",
            assignedDate = "2 days ago",
            teacherName = "Mr. Raman",
            status = "Submitted",
            submissionAttachment = "Science_Ch4_Notes_Arjun.pdf",
            submissionNote = "Attached handwritten diagram notes scanned in PDF."
        ),
        Homework(
            id = "hw3",
            subject = "English",
            classGrade = "10-A",
            title = "Write an essay on 'My Future'",
            description = "Compose a 350-word structured essay exploring your career aspirations, values, and community impact.",
            dueDate = "Monday, 10:10 AM",
            assignedDate = "3 days ago",
            teacherName = "Mrs. Sarah",
            status = "Pending"
        ),
        Homework(
            id = "hw4",
            subject = "Computer Science",
            classGrade = "10-A",
            title = "Python Functions Practice Sheet",
            description = "Implement recursive factorial and Fibonacci sequence generator functions. Submit clean commented code.",
            dueDate = "Thursday, 11:20 AM",
            assignedDate = "Yesterday",
            teacherName = "Mr. Anand",
            status = "Pending"
        ),
        Homework(
            id = "hw5",
            subject = "Social Science",
            classGrade = "10-A",
            title = "Map Work: Indian National Movement",
            description = "Locate and label Dandi, Champaran, Kheda, Amritsar, and Chauri Chaura on the outline map of India.",
            dueDate = "Wednesday, 12:10 PM",
            assignedDate = "4 days ago",
            teacherName = "Mrs. Lakshmi",
            status = "Completed"
        ),
        Homework(
            id = "hw6",
            subject = "Tamil",
            classGrade = "10-A",
            title = "Thirukkural Memorization & Meaning",
            description = "Read and write Chapter 12 couplets with explanation and grammatical notes in the workbook.",
            dueDate = "Saturday, 1:30 PM",
            assignedDate = "3 days ago",
            teacherName = "Mr. Selvam",
            status = "Completed"
        ),
        Homework(
            id = "hw7",
            subject = "Mathematics",
            classGrade = "10-A",
            title = "Trigonometry Heights and Distances",
            description = "Solve textbook questions 1 to 8 on angle of elevation and depression with neat diagrams.",
            dueDate = "Next Tuesday",
            assignedDate = "Today",
            teacherName = "Mrs. Priya",
            status = "Pending"
        ),
        Homework(
            id = "hw8",
            subject = "Science",
            classGrade = "10-A",
            title = "Physics Lab Record Verification",
            description = "Complete Ohm's Law and Focal Length experiment records with graphs and signed observations.",
            dueDate = "Next Wednesday",
            assignedDate = "Today",
            teacherName = "Mr. Raman",
            status = "Pending"
        ),
        Homework(
            id = "hw9",
            subject = "English",
            classGrade = "10-A",
            title = "Formal Letter to Editor",
            description = "Draft a formal letter highlighting traffic congestion near school zones and suggesting solutions.",
            dueDate = "Next Friday",
            assignedDate = "Yesterday",
            teacherName = "Mrs. Sarah",
            status = "Pending"
        ),
        Homework(
            id = "hw10",
            subject = "Computer Science",
            classGrade = "10-A",
            title = "SQL Database Queries Assessment",
            description = "Write SELECT queries using WHERE, GROUP BY, and ORDER BY on student report table.",
            dueDate = "In 5 days",
            assignedDate = "Today",
            teacherName = "Mr. Anand",
            status = "Pending"
        )
    )

    val initialAnnouncements = listOf(
        Announcement(
            id = "a1",
            title = "School Holiday Notice",
            date = "Sep 5, 2026",
            shortDesc = "The school will remain closed on September 7 due to a public holiday.",
            fullDesc = "Dear Parents and Students,\nPlease note that Prakash International School will observe a declared government holiday on Monday, September 7, 2026. Normal academic classes will resume on Tuesday, September 8, 2026 as per regular timetable.",
            targetAudience = "All",
            author = "Principal's Office",
            isUrgent = true
        ),
        Announcement(
            id = "a2",
            title = "Parent-Teacher Meeting (PTM)",
            date = "Sep 4, 2026",
            shortDesc = "PTM will be conducted on September 12 to discuss quarterly progress.",
            fullDesc = "The First Term Parent-Teacher Meeting for Grades 6 to 12 is scheduled for Saturday, September 12, from 9:00 AM to 1:00 PM. Parents are requested to adhere to the designated time slots sent via SMS to discuss academic performance with class educators.",
            targetAudience = "Parents",
            author = "Academic Dean"
        ),
        Announcement(
            id = "a3",
            title = "Annual Sports Day 2026 Registration",
            date = "Sep 2, 2026",
            shortDesc = "Annual Sports Day registration is now open for track and field events.",
            fullDesc = "Prakash International School's 14th Annual Athletic Meet will take place on Saturday, September 20, 2026 at the Main Sports Complex. Students interested in 100m, 400m relay, long jump, shot put, and march past should register with Physical Education teachers by September 10.",
            targetAudience = "Students",
            author = "Sports Department"
        ),
        Announcement(
            id = "a4",
            title = "Quarterly Examination Schedule Released",
            date = "Sep 1, 2026",
            shortDesc = "Exam timetable published for Grades 9 through 12 starting September 15.",
            fullDesc = "The Quarterly Assessment timetable is now officially posted on the portal. Students must carry their school ID and hall ticket. Examination timings are strictly 9:00 AM to 12:00 PM.",
            targetAudience = "All",
            author = "Examination Cell",
            isUrgent = true
        ),
        Announcement(
            id = "a5",
            title = "Inter-School Science Exhibition 'Vigyan 2026'",
            date = "Aug 28, 2026",
            shortDesc = "Submit working model proposals for renewable energy and robotics.",
            fullDesc = "Students from Grades 8 to 12 are invited to present working prototypes in Robotics, Green Energy, and AI solutions on October 18, 2026. Top 3 exhibits will receive state commendation awards.",
            targetAudience = "Students",
            author = "Science Club"
        ),
        Announcement(
            id = "a6",
            title = "National Digital Library Access Granted",
            date = "Aug 25, 2026",
            shortDesc = "Over 10,000+ reference e-books and past question papers now accessible.",
            fullDesc = "Every student's student ID is now provisioned with complimentary access to the National Digital Library consortium. Access study worksheets, audiobooks, and competitive exam modules.",
            targetAudience = "Students",
            author = "Chief Librarian"
        ),
        Announcement(
            id = "a7",
            title = "Teacher Faculty Development Workshop",
            date = "Aug 22, 2026",
            shortDesc = "Interactive pedagogy workshop on AI tools in education on Saturday.",
            fullDesc = "All teaching staff are requested to attend the certified training session on modern pedagogical assessments and adaptive learning frameworks in Auditorium B.",
            targetAudience = "Teachers",
            author = "Management Board"
        ),
        Announcement(
            id = "a8",
            title = "School Bus Route #14 Timings Adjustment",
            date = "Aug 20, 2026",
            shortDesc = "Morning pickup adjusted by 10 minutes due to metro construction work.",
            fullDesc = "Due to ongoing road widening on Poonamallee High Road, Bus Route 14 pickup times are advanced by 10 minutes starting next Monday. Updated pickup sheet is sent to parents.",
            targetAudience = "Parents",
            author = "Transport Coordinator"
        ),
        Announcement(
            id = "a9",
            title = "Blood Donation & Health Checkup Camp",
            date = "Aug 15, 2026",
            shortDesc = "Free dental and eye screening camp for all students on September 18.",
            fullDesc = "In collaboration with Apollo Hospitals, our health and wellness clinic will conduct annual preventive checkups for students and faculty.",
            targetAudience = "All",
            author = "Student Welfare"
        ),
        Announcement(
            id = "a10",
            title = "Independence Day Cultural Awards Announced",
            date = "Aug 16, 2026",
            shortDesc = "Congratulations to the winners of the patriotic song and debate competition.",
            fullDesc = "House Tagore secured 1st place in the Patriotic Symphony, followed by House Ramanujan. Certificates of excellence will be awarded during morning assembly.",
            targetAudience = "All",
            author = "Cultural Committee"
        )
    )

    val initialNotifications = listOf(
        NotificationItem("n1", "New Mathematics Homework", "Exercise 5.2 has been posted by Mrs. Priya. Due tomorrow.", "10 mins ago", NotificationCategory.HOMEWORK, isRead = false),
        NotificationItem("n2", "Attendance Record Updated", "Your attendance for today, Sep 4, has been marked: Present.", "1 hour ago", NotificationCategory.ATTENDANCE, isRead = false),
        NotificationItem("n3", "Quarterly Exam Timetable", "Quarterly examination timetable has been published. Exams begin Sep 15.", "3 hours ago", NotificationCategory.EXAM, isRead = false),
        NotificationItem("n4", "School Holiday Notice", "School will remain closed on Monday, September 7 for public holiday.", "5 hours ago", NotificationCategory.SCHOOL, isRead = false),
        NotificationItem("n5", "Science Homework Submitted", "Your Chapter 4 notes submission was successfully recorded.", "Yesterday", NotificationCategory.HOMEWORK, isRead = true),
        NotificationItem("n6", "Sports Day Registration Open", "Track and field events registration ends September 10.", "Yesterday", NotificationCategory.EVENT, isRead = true),
        NotificationItem("n7", "PTM Slot Assigned", "Your slot for PTM on September 12 is confirmed at 10:15 AM.", "2 days ago", NotificationCategory.SCHOOL, isRead = true),
        NotificationItem("n8", "Study Material Uploaded", "Mr. Anand added 'Python_Recursion_Worksheet.pdf' to Computer Science.", "3 days ago", NotificationCategory.SCHOOL, isRead = true),
        NotificationItem("n9", "Fee Receipt Generated", "Term 2 tuition fee receipt #TR-8849 is available for download.", "4 days ago", NotificationCategory.SCHOOL, isRead = true),
        NotificationItem("n10", "Annual Day Auditions", "Choir and drama auditions will take place this Wednesday at 3:00 PM.", "5 days ago", NotificationCategory.EVENT, isRead = true)
    )

    val initialEvents = listOf(
        EventItem("e1", "Parent-Teacher Meeting (PTM)", "Sep 12, 2026", "9:00 AM – 1:00 PM", "Main Academic Block", "Individual consultations between subject teachers and parents to review academic and behavioral progress.", "Academics"),
        EventItem("e2", "Quarterly Examinations", "Sep 15 – Sep 23, 2026", "9:00 AM – 12:00 PM", "Examination Hall", "First comprehensive term assessments for middle and high school grades across all subjects.", "Exams"),
        EventItem("e3", "Annual Sports Day 2026", "Sep 20, 2026", "8:00 AM – 4:00 PM", "School Athletic Stadium", "Inter-house athletic championships featuring track sprints, hurdles, relays, martial arts display, and trophy distribution.", "Sports"),
        EventItem("e4", "Annual Day Celebration", "Oct 5, 2026", "4:30 PM – 8:30 PM", "Kamarajar Auditorium", "Grand cultural showcase with orchestral performances, Shakespearean drama, classical Indian dances, and alumni guest address.", "Cultural"),
        EventItem("e5", "Science & Tech Exhibition 'Vigyan'", "Oct 18, 2026", "9:30 AM – 3:30 PM", "Science & Robotics Pavilions", "Over 120 student-designed working models in solar energy, AI automation, drone surveillance, and biochemical filtration.", "Academic"),
        EventItem("e6", "Children's Day Carnival", "Nov 14, 2026", "9:00 AM – 2:00 PM", "School Quadrangle", "Special fun day organized by teachers featuring food stalls, interactive games, music, and talent competitions.", "Celebration"),
        EventItem("e7", "Literary & Debate Fest", "Nov 28, 2026", "10:00 AM – 3:00 PM", "Conference Hall", "Inter-school extempore debate, MUN simulation, poetry recitation, and creative writing symposium.", "Literary"),
        EventItem("e8", "Founder's Memorial Lecture", "Dec 10, 2026", "10:30 AM – 12:30 PM", "Main Auditorium", "Keynote address by distinguished ISRO scientist on Space Exploration and National Innovation.", "Memorial"),
        EventItem("e9", "Christmas & New Year Assembly", "Dec 23, 2026", "9:00 AM – 11:30 AM", "Open Air Amphitheater", "Special carol performance, gratitude reflections, and commencement of winter vacation.", "Celebration"),
        EventItem("e10", "Pongal Celebration & Cultural Day", "Jan 12, 2027", "8:30 AM – 1:00 PM", "School Grounds", "Traditional festive cooking, folk dances (Karagattam, Mayilattam), and traditional sports competitions.", "Heritage")
    )

    val studyMaterialsList = listOf(
        StudyMaterial("sm1", "Mathematics", "Chapter 5: Quadratic Equations Notes", StudyMaterialType.PDF, "Mathematics_Chapter_5.pdf", "3.4 MB", 412, "Aug 20"),
        StudyMaterial("sm2", "Mathematics", "Trigonometric Identities Video Lecture", StudyMaterialType.VIDEO, "Trig_Identities_Lecture.mp4", "48.2 MB", 328, "Aug 25"),
        StudyMaterial("sm3", "Mathematics", "Heights & Distances Practice Worksheet", StudyMaterialType.WORKSHEET, "Heights_Distances_Worksheet.pdf", "1.8 MB", 504, "Aug 30"),
        StudyMaterial("sm4", "Science", "Chapter 4: Carbon Compounds Notes", StudyMaterialType.PDF, "Science_Chapter_4_Notes.pdf", "4.2 MB", 615, "Aug 18"),
        StudyMaterial("sm5", "Science", "Physics Ohm's Law & Electricity Worksheet", StudyMaterialType.WORKSHEET, "Physics_Practice_Worksheet.pdf", "2.1 MB", 480, "Aug 28"),
        StudyMaterial("sm6", "Science", "Life Processes Animation Video", StudyMaterialType.VIDEO, "Bio_Life_Processes.mp4", "52.0 MB", 390, "Aug 22"),
        StudyMaterial("sm7", "English", "Grammar: Active vs Passive Voice Guide", StudyMaterialType.PDF, "English_Grammar_Voice.pdf", "1.5 MB", 365, "Aug 15"),
        StudyMaterial("sm8", "English", "Sample Descriptive & Analytical Essays", StudyMaterialType.REFERENCE, "Sample_Essays_Grade10.epub", "2.8 MB", 298, "Aug 24"),
        StudyMaterial("sm9", "Computer Science", "Python Recursion & Functions Handbook", StudyMaterialType.PDF, "Python_Functions_Handbook.pdf", "3.1 MB", 442, "Aug 27"),
        StudyMaterial("sm10", "Computer Science", "SQL Database Table Schema & Queries", StudyMaterialType.WORKSHEET, "SQL_Practice_Queries.pdf", "1.9 MB", 380, "Aug 29"),
        StudyMaterial("sm11", "Social Science", "Indian Nationalism Timeline & Map Guide", StudyMaterialType.PDF, "Social_Nationalism_Guide.pdf", "5.6 MB", 520, "Aug 19"),
        StudyMaterial("sm12", "Social Science", "Federalism & Democratic Politics Summary", StudyMaterialType.REFERENCE, "Democratic_Politics_Summary.epub", "2.2 MB", 310, "Aug 26"),
        StudyMaterial("sm13", "Tamil", "Thirukkural Explanation & Grammar", StudyMaterialType.PDF, "Tamil_Thirukkural_Ch12.pdf", "2.4 MB", 375, "Aug 21"),
        StudyMaterial("sm14", "Tamil", "Tamil Literature Essay Writing Model", StudyMaterialType.REFERENCE, "Tamil_Literature_Models.pdf", "1.7 MB", 260, "Aug 23")
    )

    val upcomingExams = listOf(
        Exam("ex1", "Quarterly Examination", "Term 1", "Mathematics", "15 Sep 2026", "9:00 AM – 12:00 PM", "Hall A", 100),
        Exam("ex2", "Quarterly Examination", "Term 1", "Science", "17 Sep 2026", "9:00 AM – 12:00 PM", "Hall A", 100),
        Exam("ex3", "Quarterly Examination", "Term 1", "English", "19 Sep 2026", "9:00 AM – 12:00 PM", "Hall A", 100),
        Exam("ex4", "Quarterly Examination", "Term 1", "Tamil", "21 Sep 2026", "9:00 AM – 12:00 PM", "Hall A", 100),
        Exam("ex5", "Quarterly Examination", "Term 1", "Social Science", "23 Sep 2026", "9:00 AM – 12:00 PM", "Hall A", 100),
        Exam("ex6", "Quarterly Examination", "Term 1", "Computer Science", "25 Sep 2026", "9:00 AM – 12:00 PM", "IT Lab 1", 100)
    )

    val studentResults = listOf(
        ExamResult("Tamil", 91, 100, "A1", "Excellent vocabulary and essay structure."),
        ExamResult("English", 88, 100, "A2", "Very good reading comprehension and grammar."),
        ExamResult("Mathematics", 94, 100, "A1", "Outstanding problem solving and logical steps."),
        ExamResult("Science", 90, 100, "A1", "Clear conceptual understanding in Physics and Chemistry."),
        ExamResult("Social Science", 87, 100, "A2", "Good analytical answers and accurate map skills."),
        ExamResult("Computer Science", 96, 100, "A1", "Exceptional code logic and algorithmic reasoning.")
    )

    val monthlyAttendanceDays = (1..30).map { day ->
        val status = when {
            day % 7 == 0 -> AttendanceStatus.HOLIDAY
            day == 16 -> AttendanceStatus.ABSENT
            day == 24 -> AttendanceStatus.LEAVE
            day == 7 -> AttendanceStatus.HOLIDAY
            else -> AttendanceStatus.PRESENT
        }
        val dayOfWeek = when (day % 7) {
            1 -> "Mon"
            2 -> "Tue"
            3 -> "Wed"
            4 -> "Thu"
            5 -> "Fri"
            6 -> "Sat"
            else -> "Sun"
        }
        AttendanceDay(day, dayOfWeek, status, if (status == AttendanceStatus.ABSENT) "Sick Leave" else "")
    }
}
