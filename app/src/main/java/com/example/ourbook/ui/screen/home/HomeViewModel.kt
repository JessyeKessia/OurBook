package com.example.ourbook.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ourbook.data.model.Book
import com.example.ourbook.data.repository.OurBookRepositoryLocal // Importa o local novo
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: OurBookRepositoryLocal // Agora recebe apenas o repositório local
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Success()) // Inicia em Success para o combine funcionar
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val filteredBooks: StateFlow<List<Book>> =
        combine(_uiState, repository.getBooksFlow()) { state, localBooks -> // Busca o Flow do repositório
            if (state is HomeUiState.Success) {
                localBooks.filter { book ->
                    val matchSearch = book.title.contains(state.search, ignoreCase = true) ||
                            book.author.contains(state.search, ignoreCase = true)
                    val matchFilter = when (state.filter) {
                        "Disponíveis" -> book.isAvailable
                        "Emprestados" -> !book.isAvailable
                        else -> true
                    }
                    matchSearch && matchFilter
                }
            } else {
                emptyList()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchChange(value: String) {
        val currentState = _uiState.value
        if (currentState is HomeUiState.Success) {
            _uiState.value = currentState.copy(search = value)
        }
    }

    fun onFilterChange(value: String) {
        val currentState = _uiState.value
        if (currentState is HomeUiState.Success) {
            _uiState.value = currentState.copy(filter = value)
        }
    }
}