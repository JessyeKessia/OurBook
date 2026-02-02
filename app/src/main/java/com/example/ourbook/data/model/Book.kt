package com.example.ourbook.data.model // Verifique se o package condiz com a pasta

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var title: String = "",
    var author: String = "",
    var isAvailable: Boolean = true,
    var dueDate: String? = null,
    var coverUrl: String? = null,

    val loanUserId: Long? = null,
) {
    val availabilityText: String
        get() = if (isAvailable) "Disponível" else "Indisponível"
}