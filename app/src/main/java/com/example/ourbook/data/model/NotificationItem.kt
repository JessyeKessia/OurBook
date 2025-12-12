package com.example.ourbook.data.model

data class NotificationItem(
    val id: String,
    val type: String,      // "prazo", "recompensa", "atraso"
    val title: String,
    val message: String,
    val timeAgo: String
)