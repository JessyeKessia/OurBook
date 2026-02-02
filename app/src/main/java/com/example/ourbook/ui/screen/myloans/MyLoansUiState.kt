package com.example.ourbook.ui.screen.myloans

import com.example.ourbook.data.model.Book

data class MyLoansUiState(
    val isLoading: Boolean = true,
    val loans: List<Book> = emptyList(),
    val error: String? = null
)
