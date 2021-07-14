package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Medium Test to test the repository
@MediumTest
class RemindersLocalRepositoryTest {

    //: Add testing implementation to the RemindersLocalRepository.kt

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var remindersLocalRepository: RemindersLocalRepository
    private lateinit var database: RemindersDatabase

    @Before
    fun setup() {
        // Using an in-memory database for testing, because it doesn't survive killing the process.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        remindersLocalRepository =
            RemindersLocalRepository(
                database.reminderDao(),
                Dispatchers.Main
            )
    }

    @After
    fun cleanUp() {
        database.close()
    }

    private fun getSampleReminder(): ReminderDTO {
        return ReminderDTO(
            title = "title",
            description = "description",
            location = "location",
            latitude = 0.0,
            longitude = 0.0
        )
    }

    @Test
    fun insertReminderAndGetById() = runBlocking {
        // GIVEN - Insert a Location Reminder in the Database.
        val reminder = getSampleReminder()
        remindersLocalRepository.saveReminder(reminder)

        // WHEN - Get the location reminder by id from the database.
        val loaded = remindersLocalRepository.getReminder(reminder.id)

        // THEN - The loaded data contains the expected values.
        assertThat(loaded is Result.Success, `is`(true))
        loaded as Result.Success

        assertThat(loaded.data.title, `is`(reminder.title))
        assertThat(loaded.data.description, `is`(reminder.description))
        assertThat(loaded.data.latitude, `is`(reminder.latitude))
        assertThat(loaded.data.longitude, `is`(reminder.longitude))
        assertThat(loaded.data.location, `is`(reminder.location))
    }

    @Test
    fun deleteAllReminders_getById() = runBlocking {
        val sampleReminder = getSampleReminder()

        // GIVEN - Insert & Delete a Location Reminder.
        remindersLocalRepository.saveReminder(sampleReminder)
        remindersLocalRepository.deleteAllReminders()

        // WHEN - Get the location reminder by id from the database.
        val loaded = remindersLocalRepository.getReminder(sampleReminder.id)

        // THEN - The loaded data should not in the expected values.
        assertThat(loaded is Result.Error, `is`(true))
        loaded as Result.Error
        assertThat(loaded.message, `is`("Reminder not found!"))
    }

}