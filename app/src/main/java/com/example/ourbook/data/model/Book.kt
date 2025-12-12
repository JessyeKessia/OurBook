package com.example.ourbook.data.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val status: String,      // "Emprestado", "Atrasado", "Devolvido", "Disponível"
    val dueDate: String? = null,
    val coverUrl: String? = null
)