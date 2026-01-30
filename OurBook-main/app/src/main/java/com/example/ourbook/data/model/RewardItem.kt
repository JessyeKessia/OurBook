package com.example.ourbook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId

@Entity(tableName = "rewards")

data class RewardItem(

    @PrimaryKey
    @DocumentId
    val id: String,
    val title: String,
    val description: String,
    val costCoins: Int = 0,
    val isLoading: Boolean = false
)