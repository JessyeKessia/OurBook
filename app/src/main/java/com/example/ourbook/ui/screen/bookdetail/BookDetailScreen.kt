package com.example.ourbook.ui.screen.bookdetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    bookId: String,
    onBack: () -> Unit,
    onRegisterLoan: () -> Unit,
    viewModel: BookDetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val book = viewModel.getBook(bookId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhe do livro") },
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
            Text(
                text = book.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = book.author,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(16.dp))
            Text("Status: ${book.status}")
            book.dueDate?.let {
                Spacer(Modifier.height(4.dp))
                Text("Devolução: $it")
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onRegisterLoan,
                modifier = Modifier.fillMaxWidth(),
                enabled = book.status == "Disponível"
            ) {
                Text("Registrar Empréstimo")
            }
        }
    }
}
