package com.example.ourbook.ui.screen.myloans

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ourbook.data.model.Book
import com.example.ourbook.ui.screen.loan.MyLoansViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLoansScreen(
    onBack: () -> Unit,
    onOpenBook: (Long) -> Unit,
    viewModel: MyLoansViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    var selectedBookId by remember { mutableStateOf<Long?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null && selectedBookId != null) {
            viewModel.returnBook(selectedBookId!!, uri)
            selectedBookId = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meus Empréstimos") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.loans.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Você não possui livros emprestados")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    items(state.loans) { book ->
                        LoanCard(
                            book = book,
                            onOpenBook = { onOpenBook(book.id) },
                            onReturnClick = {
                                selectedBookId = book.id
                                imagePicker.launch("image/*")
                            }
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}
@Composable
fun LoanCard(
    book: Book,
    onOpenBook: () -> Unit,
    onReturnClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenBook() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(book.title, style = MaterialTheme.typography.titleMedium)
            Text(book.author)

            Spacer(Modifier.height(8.dp))
            Text("Devolução: ${book.dueDate ?: "N/A"}")

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onReturnClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.PhotoCamera, contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text("Devolver")
            }
        }
    }
}

