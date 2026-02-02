package com.example.ourbook.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ourbook.data.repository.OurBookRepositoryLocal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: OurBookRepositoryLocal
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChange(v: String) = _uiState.update { it.copy(name = v) }
    fun onEmailChange(v: String) = _uiState.update { it.copy(email = v) }
    fun onSenhaChange(v: String) = _uiState.update { it.copy(senha = v) }
    fun onConfirmSenhaChange(v: String) =
        _uiState.update { it.copy(confirmSenha = v) }

    fun register() {
        viewModelScope.launch {
            val s = _uiState.value

            if (s.name.isBlank() || s.email.isBlank() ||
                s.senha.isBlank() || s.confirmSenha.isBlank()
            ) {
                _uiState.update { it.copy(error = "Preencha todos os campos") }
                return@launch
            }

            if (s.senha != s.confirmSenha) {
                _uiState.update { it.copy(error = "As senhas não conferem") }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, error = null) }

            val success = repository.registerUser(
                name = s.name,
                email = s.email,
                senha = s.senha
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    isSuccess = success,
                    error = if (!success) "E-mail já cadastrado" else null
                )
            }
        }
    }
}
