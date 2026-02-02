package com.example.ourbook.ui.screen.loan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ourbook.data.model.Book
import com.example.ourbook.data.repository.OurBookRepositoryLocal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class LoanRegisterUiState(
    val userName: String = "",
    val dueDate: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val isValid: Boolean get() = userName.isNotBlank() && dueDate.isNotBlank()
}

class LoanRegisterViewModel(
    private val repository: OurBookRepositoryLocal // Injetado via Koin
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoanRegisterUiState())
    val uiState: StateFlow<LoanRegisterUiState> = _uiState.asStateFlow()

    private val _book = MutableStateFlow<Book?>(null)
    val book: StateFlow<Book?> = _book.asStateFlow()

    fun loadBook(bookId: Long) {
        viewModelScope.launch {
            // Agora busca no banco de dados local do seu celular
            val loadedBook = repository.getBookById(bookId)
            _book.value = loadedBook
        }
    }

    fun confirmLoan(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val currentBook = _book.value ?: return@launch
            val dueDate = calculateDueDate()

            // Registra o empréstimo no Room
            repository.registerLoan(currentBook.id, dueDate)
            onSuccess()
        }
    }

    private fun calculateDueDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 20)
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(calendar.time)
    }
}