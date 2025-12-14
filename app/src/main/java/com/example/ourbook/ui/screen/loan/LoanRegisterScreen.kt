package com.example.ourbook.ui.screen.loan

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    val book by viewModel.book.collectAsState()

    // Carregar o livro quando o Composable iniciar
    LaunchedEffect(bookId) {
        viewModel.loadBook(bookId)
    }

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
            if (book == null) {
                Text("Carregando livro...")
            } else {
                Text("Livro: ${book!!.title}")
                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.confirmLoan { onBack() } },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = book!!.isAvailable && !state.isLoading
                ) {
                    Text(if (state.isLoading) "Registrando..." else "Confirmar Empréstimo")
                }
            }

            state.error?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

