package com.udacity.project4.locationreminders.data.local

import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test

class RemindersLocalRepositoryTest {
    private val reminder1 = ReminderDTO("Title1", "Description1", "Test Location1", 566.0, 456545.0)
    private val reminder2 = ReminderDTO("Title2", "Description2", "Test Location2", 566.0, 456545.0)

    private val localTasks = listOf(reminder1).sortedBy { it.id }
    private val newTasks = listOf(reminder1).sortedBy { it.id }

    private lateinit var tasksLocalDataSource: FakeDataSource

    // Class under test
    private lateinit var tasksRepository: RemindersLocalRepository

    @Before
    fun createRepository() {
        tasksLocalDataSource = FakeDataSource(localTasks.toMutableList())
        // Get a reference to the class under test
        tasksRepository = RemindersLocalRepository(
            //  Dispatchers.Unconfined should be replaced with Dispatchers.Main
            //  this requires understanding more about coroutines + testing
            //  so we will keep this as Unconfined for now.
            tasksLocalDataSource, Dispatchers.Unconfined
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSource() = runBlocking {
        // When tasks are requested from the tasks repository
        val tasks = tasksRepository.getTasks(true) as Result.Success

        // Then tasks are loaded from the remote data source
        MatcherAssert.assertThat(tasks.data, IsEqual(remoteTasks))
    }

}