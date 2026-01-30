package com.example.ourbook.ui.screen.home

import com.example.ourbook.data.model.Book

sealed class HomeUiState {
    object Idle : HomeUiState() // Estado inicial
    object Loading : HomeUiState() // Quando o app está buscando dados
    data class Success(
        val books: List<Book>,
        val search: String = "",
        val filter: String = "Todos"
    ) : HomeUiState() // Quando os dados chegaram com sucesso
    data class Error(val message: String) : HomeUiState() // Quando algo deu errado
}