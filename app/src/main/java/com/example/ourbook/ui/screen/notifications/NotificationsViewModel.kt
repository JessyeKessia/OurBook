package com.example.ourbook.ui.screen.notifications

import androidx.lifecycle.ViewModel
import com.example.ourbook.data.OurBookRepository
import com.example.ourbook.data.model.NotificationItem
import kotlinx.coroutines.flow.Flow

class NotificationsViewModel(
    private val repository: OurBookRepository = OurBookRepository()
) : ViewModel() {

    val notifications: Flow<List<NotificationItem>> = repository.notifications
}
