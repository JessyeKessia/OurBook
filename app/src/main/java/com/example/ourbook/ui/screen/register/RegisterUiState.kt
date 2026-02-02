package com.example.ourbook.ui.screen.register

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val senha: String = "",
    val confirmSenha: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)