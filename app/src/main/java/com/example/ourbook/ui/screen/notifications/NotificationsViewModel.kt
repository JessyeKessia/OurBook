package com.example.ourbook.ui.screen.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// import com.example.ourbook.data.repository.OurBookRepository
import com.example.ourbook.data.remote.OurBookRepositoryRemote
import com.example.ourbook.data.model.NotificationItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val repository: OurBookRepositoryRemote = OurBookRepositoryRemote
) : ViewModel() {

    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications = _notifications.asStateFlow()

    init {
        viewModelScope.launch {
            _notifications.value = repository.getNotifications()
        }
    }
}
