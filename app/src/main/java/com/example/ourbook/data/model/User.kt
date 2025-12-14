package com.example.ourbook.data.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val senha: String = "",
    val coins: Int = 0
)
