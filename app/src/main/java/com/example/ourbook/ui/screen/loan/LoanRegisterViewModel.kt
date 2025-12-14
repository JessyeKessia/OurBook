package com.example.ourbook.ui.screen.loan


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ourbook.data.model.Book
import com.example.ourbook.data.remote.OurBookRepositoryRemote
// import com.example.ourbook.data.repository.OurBookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import java.text.SimpleDateFormat

data class LoanRegisterUiState(
    val userName: String = "",
    val dueDate: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val isValid: Boolean get() = userName.isNotBlank() && dueDate.isNotBlank()
}

class LoanRegisterViewModel(
    private val repository: OurBookRepositoryRemote = OurBookRepositoryRemote
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoanRegisterUiState>(LoanRegisterUiState())
    val uiState: StateFlow<LoanRegisterUiState> = _uiState.asStateFlow()

    private val _book = MutableStateFlow<Book?>(null)
    val book: StateFlow<Book?> = _book.asStateFlow()

    fun loadBook(bookId: String) {
        viewModelScope.launch {
            val loadedBook = repository.getBookById(bookId)
            _book.value = loadedBook
        }
    }

    fun confirmLoan(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val currentBook = _book.value ?: return@launch
            val dueDate = calculateDueDate() // 20 dias a partir de hoje

            repository.registerLoan(currentBook.id, dueDate)
            onSuccess()
        }
    }

    private fun calculateDueDate(): String {
        val calendar = java.util.Calendar.getInstance()
        calendar.add(java.util.Calendar.DAY_OF_YEAR, 20)
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        return sdf.format(calendar.time)
    }
}