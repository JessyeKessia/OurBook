package com.example.ourbook.ui.screen.bookdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ourbook.data.model.Book
import com.example.ourbook.data.repository.OurBookRepositoryLocal // Importa o Local
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val repository: OurBookRepositoryLocal // Injetado pelo Koin
) : ViewModel() {

    private val _book = MutableStateFlow<Book?>(null)
    val book: StateFlow<Book?> = _book.asStateFlow()

    fun loadBook(bookId: Long) {
        viewModelScope.launch {
            _book.value = repository.getBookById(bookId) // Agora busca no banco local
        }
    }
}
