# Prakash International School — Branded Mobile App Demo

> **"Your School. Your Own App."**  
> A production-grade commercial School Management Mobile Application demo designed for presentations to school principals, trustees, and management boards.

---

## 🌟 Demo Concept & Value Proposition

Every educational institution can deploy its own branded Android application with custom:
* School name, logo crest, and academic motto
* Institutional brand colors (Primary & Accent palettes)
* Dedicated Play Store listing
* Role-tailored experiences for **Students**, **Parents**, **Teachers**, and **Administrators**

---

## 🚀 Live Demo Features & Personas

The app includes a persistent **Sales Demo Role Switcher** at the top of the interface for instantaneous transitions during live demonstrations:

### 1. Student Portal (`Arjun Kumar • Grade 10-A`)
* **Live Dashboard**: Period timetable, today's schedule, homework counters, quick actions
* **Homework Manager**: Filter by All, Pending, and Completed; interactive assignment submission and completion
* **Academics Suite**:
  * Weekly class timetable with current period indicators
  * Study materials with downloadable PDFs, video lectures, and practice worksheets
  * Exam schedules (Term & Quarterly)
  * Marksheet & grade performance cards (Term 1: 91% A+)
* **Updates & Broadcasts**: School announcements, categorized notifications (Homework, Exams, Attendance), and campus events calendar
* **Digital Student ID**: Official photo ID card with QR code, roll number, admission details, and emergency contact

### 2. Parent Portal (`Mr. Kumar`)
* **Reassuring Overview**: Attendance percentage (`92%`), pending homework countdown, next upcoming exam alert
* **Child Profile Switcher**: Detailed monitoring for Arjun Kumar (Grade 10-A)
* **One-Tap School Contact**: Direct phone, email, and campus address access
* **Academic Transparency**: Full visibility into homework and exam dates

### 3. Teacher Portal (`Mrs. Priya • Mathematics Faculty`)
* **Assigned Classes**: Direct access to Grade 10-A, Grade 9-B, and Grade 8-A
* **Roll Call Attendance**: One-tap "Mark All Present", granular Present (P), Absent (A), and Leave (L) toggles, and instant submission
* **Publish Homework**: Target class selection, title, instructions, due date, and document attachments
* **Class Announcements**: Broadcast notes and study instructions directly to class rosters

### 4. Administrator / Principal Portal (`School Management`)
* **Campus KPIs**: Total Enrolled Students (`1,248`), Faculty (`86`), Today's Attendance (`94.2%`), Bus fleet & fee status
* **Student Directory**: Live search by name or admission number, filtered by grade
* **Push Notification Dispatcher**: Send instant FCM push broadcasts to All, Parents, Students, or Teachers with live delivery metrics
* **White-Label Brand Customizer**:
  * Real-time preview of institutional branding
  * Color theme presets (Prakash Blue, Oxford Navy, Emerald Academy, Crimson High)
  * Custom full school name, short name, motto, and contact details with instant live theme updates across the entire app

---

## 🛠️ Architecture & Tech Stack

* **Platform**: Modern Android with Jetpack Compose & Material Design 3
* **State Management**: Reactive `StateFlow` with unidirectional data flow via `SchoolRepository`
* **Local Persistence & Demo Safety**: Offline-first mock data engine for glitch-free presentations without network dependency
* **Testing**: Robolectric unit tests verifying business logic, role switching, homework submission, and branding updates
