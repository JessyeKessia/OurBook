package com.example.ourbook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loans")
data class LoanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val bookId: Long,      // FK lógica → Book.id
    val userId: Long,   // FK lógica → User.email

    val dueDate: String,
    val returned: Boolean = false,
    val returnPhotoUri: String? = null
)
