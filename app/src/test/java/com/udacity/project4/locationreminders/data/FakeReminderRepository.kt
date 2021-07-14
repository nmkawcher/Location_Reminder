package com.udacity.project4.locationreminders.data

import androidx.lifecycle.MutableLiveData
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

class FakeReminderRepository: ReminderDataSource {
    var reminderServiceData: LinkedHashMap<String, ReminderDTO> = LinkedHashMap()

    private val observableReminders = MutableLiveData<Result<List<ReminderDTO>>>()

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllReminders() {
        TODO("Not yet implemented")
    }
}