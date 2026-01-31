package com.example.ourbook.ui.screen.notifications

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ourbook.data.model.NotificationItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    onBack: () -> Unit,
    viewModel: NotificationsViewModel = koinViewModel()
) {
    // Coleta a lista de notificações do StateFlow
    val notifications by viewModel.notifications.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notificações") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(notifications) { item ->
                NotificationCard(notification = item)
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun NotificationCard(notification: NotificationItem) {
    val icon = when (notification.type) {
        "prazo" -> Icons.Filled.Schedule
        "recompensa" -> Icons.Filled.Star
        "atraso" -> Icons.Filled.Error
        else -> Icons.Filled.Notifications
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(32.dp))
            Spacer(Modifier.width(12.dp))
            Column {
                Text(notification.title, style = MaterialTheme.typography.titleMedium)
                Text(notification.message, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(4.dp))
                Text(notification.timeAgo, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
