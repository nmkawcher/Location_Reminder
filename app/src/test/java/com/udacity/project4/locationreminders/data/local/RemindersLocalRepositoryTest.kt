package com.udacity.project4.locationreminders.data.local

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import junit.framework.TestCase

class RemindersLocalRepositoryTest : TestCase() {
    private val task1 = ReminderDTO("Title1", "Description1", "Test Location1", 566.0, 456545.0)
    private val task2 = ReminderDTO("Title2", "Description2", "Test Location2", 566.0, 456545.0)

    private val localTasks = listOf(task1).sortedBy { it.id }
    private val newTasks = listOf(task1).sortedBy { it.id }
}