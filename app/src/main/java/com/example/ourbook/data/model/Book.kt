package com.example.ourbook.data.model

import com.google.firebase.firestore.DocumentId

data class Book(
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