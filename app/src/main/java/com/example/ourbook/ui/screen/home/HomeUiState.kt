package com.example.ourbook.ui.screen.home

import com.example.ourbook.data.model.Book

sealed class HomeUiState(
    open val search: String = "", // acessível em qualquer estado
    open val filter: String = "Todos"
) {
    object Idle : HomeUiState()
    object Loading : HomeUiState()
    data class Success(
        val books: List<Book>,
        override val search: String = "",
        override val filter: String = "Todos"
    ) : HomeUiState(search, filter)
    data class Error(val message: String) : HomeUiState()
}