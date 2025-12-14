package com.example.ourbook.data.model

import com.google.firebase.firestore.DocumentId

data class RewardItem(

    @DocumentId
    val id: String,
    val title: String,
    val description: String,
    val costCoins: Int = 0,
    val isLoading: Boolean = false
)