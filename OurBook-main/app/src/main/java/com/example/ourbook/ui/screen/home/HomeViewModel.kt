package com.example.ourbook.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ourbook.data.local.BookDao
import com.example.ourbook.data.remote.OurBookRepositoryRemote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel recebendo dependências via Koin
class HomeViewModel(
    private val remoteRepository: OurBookRepositoryRemote,
    private val bookDao: BookDao
) : ViewModel() {

    // Segue o manual: inicia com o estado Idle
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            // 1. Mudança para o estado de Carregamento
            _uiState.value = HomeUiState.Loading

            try {
                // 2. Busca os dados (Lógica de negócio)
                val books = remoteRepository.getBooks()
                bookDao.insertBooks(books) // Salva no Room para persistência local

                // 3. Sucesso: Passa os dados para a UI
                _uiState.value = HomeUiState.Success(books = books)
            } catch (e: Exception) {
                // 4. Erro: Informa a UI sobre a falha
                _uiState.value = HomeUiState.Error(e.message ?: "Erro ao carregar livros")
            }
        }
    }

    // Funções de atualização precisam verificar se o estado atual é "Success"
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
