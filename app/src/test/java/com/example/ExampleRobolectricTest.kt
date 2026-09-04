package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.config.SchoolConfig
import com.example.data.MockSchoolData
import com.example.data.SchoolRepository
import com.example.model.AttendanceStatus
import com.example.model.UserRole
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Prakash School", appName)
  }

  @Test
  fun `verify mock students data loaded`() {
    assertTrue(MockSchoolData.studentsList.size >= 20)
    val arjun = MockSchoolData.studentsList.first()
    assertEquals("Arjun Kumar", arjun.name)
    assertEquals("10", arjun.grade)
    assertEquals("A", arjun.section)
    assertEquals(24, arjun.rollNo)
    assertEquals(92, arjun.attendancePct)
  }

  @Test
  fun `verify role switching`() {
    SchoolRepository.switchRole(UserRole.PARENT)
    assertEquals(UserRole.PARENT, SchoolRepository.currentRole.value)

    SchoolRepository.switchRole(UserRole.TEACHER)
    assertEquals(UserRole.TEACHER, SchoolRepository.currentRole.value)

    SchoolRepository.switchRole(UserRole.ADMIN)
    assertEquals(UserRole.ADMIN, SchoolRepository.currentRole.value)

    SchoolRepository.switchRole(UserRole.STUDENT)
    assertEquals(UserRole.STUDENT, SchoolRepository.currentRole.value)
  }

  @Test
  fun `verify homework submission and completion`() {
    val initialHw = SchoolRepository.homeworkList.value.first()
    SchoolRepository.markHomeworkCompleted(initialHw.id)

    val updatedHw = SchoolRepository.homeworkList.value.first { it.id == initialHw.id }
    assertEquals("Completed", updatedHw.status)
  }

  @Test
  fun `verify school config branding update`() {
    val newConfig = SchoolConfig(
      schoolName = "Greenwood Academy",
      shortName = "GWA",
      tagline = "Excellence in Action"
    )
    SchoolRepository.updateSchoolConfig(newConfig)
    assertEquals("Greenwood Academy", SchoolRepository.schoolConfig.value.schoolName)
    assertEquals("GWA", SchoolRepository.schoolConfig.value.shortName)
  }
}

