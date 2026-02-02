package com.example.ourbook.ui.screen.loan

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ourbook.data.repository.OurBookRepositoryLocal
import com.example.ourbook.ui.screen.myloans.MyLoansUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyLoansViewModel(
    private val repository: OurBookRepositoryLocal
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyLoansUiState())
    val uiState: StateFlow<MyLoansUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.loadLoggedUser()
            loadMyLoans()
        }
    }

    private fun loadMyLoans() {
        viewModelScope.launch {
            val user = repository.currentUser.value
            if (user == null) {
                _uiState.value = MyLoansUiState(isLoading = false)
                return@launch
            }

            val loans = repository.getLoansByUser(user.id)

            _uiState.value = MyLoansUiState(
                isLoading = false,
                loans = loans
            )
        }
    }

    fun returnBook(bookId: Long, photoUri: Uri) {
        viewModelScope.launch {
            // 🔹 aqui você pode enviar a foto pro backend futuramente
            repository.returnLoan(bookId)
            loadMyLoans() // atualiza a lista
        }
    }
}
