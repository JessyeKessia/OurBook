package com.example.ourbook.data.model

import com.google.firebase.firestore.DocumentId

@Entity(tableName = "books")
data class Book(
    @PrimaryKey // chave primaria para o room
    @DocumentId
    var id: String = "",
    var title: String = "",
    var author: String = "",
    var isAvailable: Boolean = true,
    var dueDate: String? = null,
    var coverUrl: String? = null
) {
    val availabilityText: String
        get() = if (isAvailable) "Disponível" else "Indisponível"

}