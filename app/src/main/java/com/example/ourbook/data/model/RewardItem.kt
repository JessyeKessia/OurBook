package com.example.ourbook.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId


@Entity(tableName = "rewards")

data class RewardItem(

    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val costCoins: Int = 0,
    val isLoading: Boolean = false
)