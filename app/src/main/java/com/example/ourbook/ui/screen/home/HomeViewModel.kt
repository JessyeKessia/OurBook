package com.example.ourbook.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// import com.example.ourbook.data.repository.OurBookRepository
import com.example.ourbook.data.model.Book
import com.example.ourbook.data.remote.OurBookRepositoryRemote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted

data class HomeUiState(
    val search: String = "",
    val filter: String = "Todos",
    val books: List<Book> = emptyList()
)
class HomeViewModel(
    private val repository: OurBookRepositoryRemote = OurBookRepositoryRemote
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // Fluxo de livros filtrados reativos
    val filteredBooks: StateFlow<List<Book>> = _uiState
        .map { state ->
            state.books.filter { book ->
                val matchSearch = book.title.contains(state.search, true) ||
                        book.author.contains(state.search, true)
                val matchFilter = when (state.filter) {
                    "Emprestados" -> !book.isAvailable // livros emprestados
                    "Disponíveis" -> book.isAvailable // livros disponíveis
                    else -> true
                }
                matchSearch && matchFilter
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            val books = repository.getBooks()
            _uiState.update { it.copy(books = books) }
        }
    }

    fun onSearchChange(value: String) {
        _uiState.update { it.copy(search = value) }
    }

    fun onFilterChange(value: String) {
        _uiState.update { it.copy(filter = value) }
    }
}
