package com.example.ourbook.ui.screen.bookdetail

import androidx.lifecycle.ViewModel
import com.example.ourbook.data.OurBookRepository
import com.example.ourbook.data.model.Book

class BookDetailViewModel(
    private val repository: OurBookRepository = OurBookRepository()
) : ViewModel() {

    fun getBook(id: String): Book {
        return repository.books.value.firstOrNull { it.id == id }
            ?: Book(id, "Livro não encontrado", "Autor desconhecido", "Indisponível")
    }
}
