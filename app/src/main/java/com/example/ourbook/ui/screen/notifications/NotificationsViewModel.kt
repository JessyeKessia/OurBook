package com.example.ourbook.ui.screen.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ourbook.data.model.NotificationItem
import com.example.ourbook.data.repository.OurBookRepositoryLocal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val repository: OurBookRepositoryLocal // Injetado pelo Koin
) : ViewModel() {

    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications = _notifications.asStateFlow()

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            // Agora busca as notificações do banco local
            _notifications.value = repository.getNotifications()
        }
    }
}