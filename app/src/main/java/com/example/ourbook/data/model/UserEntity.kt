package com.example.ourbook.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")

data class UserEntity(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val senha: String = "",
    val coins: Int = 0,
    val isLogged: Boolean = false
)