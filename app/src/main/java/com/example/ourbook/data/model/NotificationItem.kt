package com.example.ourbook.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId

@Entity(tableName = "notifications") // Transforma em tabela do Room

data class NotificationItem(

    @PrimaryKey // Define o ID como chave primária para o banco local
    val id: Long = 0L,
    val type: String,      // "prazo", "recompensa", "atraso"
    val title: String,
    val message: String,
    val timeAgo: String
)