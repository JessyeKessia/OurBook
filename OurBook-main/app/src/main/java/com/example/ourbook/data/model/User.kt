package com.example.ourbook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId

@Entity(tableName = "users")

data class User(

    @Primarykey
    @DocumentId
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val senha: String = "",
    val coins: Int = 0
)
