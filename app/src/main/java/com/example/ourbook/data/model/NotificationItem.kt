package com.example.ourbook.data.model
import com.google.firebase.firestore.DocumentId


data class NotificationItem(
    @DocumentId
    val id: String,
    val type: String,      // "prazo", "recompensa", "atraso"
    val title: String,
    val message: String,
    val timeAgo: String
)