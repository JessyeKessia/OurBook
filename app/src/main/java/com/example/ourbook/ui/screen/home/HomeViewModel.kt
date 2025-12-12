package com.example.ourbook.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ourbook.data.OurBookRepository
import com.example.ourbook.data.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val search: String = "",
    val filter: String = "Todos",
    val books: List<Book> = emptyList()
)

class HomeViewModel(
    private val repository: OurBookRepository = OurBookRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.books.collectLatest { list ->
                _uiState.update { it.copy(books = list) }
            }
        }
    }

    fun onSearchChange(value: String) {
        _uiState.update { it.copy(search = value) }
    }

    fun onFilterChange(value: String) {
        _uiState.update { it.copy(filter = value) }
    }

    fun filteredBooks(): List<Book> {
        val s = uiState.value.search
        val f = uiState.value.filter

        return uiState.value.books.filter { book ->
            val matchSearch = book.title.contains(s, ignoreCase = true) ||
                    book.author.contains(s, ignoreCase = true)

            val matchFilter = when (f) {
                "Ativos" -> book.status == "Emprestado" || book.status == "Atrasado"
                "Concluídos" -> book.status == "Devolvido"
                else -> true
            }

            matchSearch && matchFilter
        }
    }
}
