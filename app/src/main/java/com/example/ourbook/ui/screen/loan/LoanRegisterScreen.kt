package com.example.ourbook.ui.screen.loan

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanRegisterScreen(
    bookId: String,
    onBack: () -> Unit,
    viewModel: LoanRegisterViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Empréstimo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Livro ID: $bookId")
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = state.userName,
                onValueChange = viewModel::onUserNameChange,
                label = { Text("Nome do usuário") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.dueDate,
                onValueChange = viewModel::onDueDateChange,
                label = { Text("Data de devolução (dd/mm/aaaa)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { viewModel.confirmLoan(bookId) { onBack() } },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.isValid && !state.isLoading
            ) {
                Text(if (state.isLoading) "Salvando..." else "Confirmar Empréstimo")
            }

            state.error?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
