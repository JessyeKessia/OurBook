package com.example.ourbook.ui.screen.loan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ourbook.data.OurBookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoanRegisterUiState(
    val userName: String = "",
    val dueDate: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val isValid: Boolean get() = userName.isNotBlank() && dueDate.isNotBlank()
}

class LoanRegisterViewModel(
    private val repository: OurBookRepository = OurBookRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoanRegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onUserNameChange(value: String) {
        _uiState.update { it.copy(userName = value) }
    }

    fun onDueDateChange(value: String) {
        _uiState.update { it.copy(dueDate = value) }
    }

    fun confirmLoan(bookId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                repository.registerLoan(bookId, uiState.value.userName, uiState.value.dueDate)
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Erro ao registrar empréstimo") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
