package com.example.ourbook.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ourbook.data.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onOpenBook: (String) -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenRewards: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val books = viewModel.filteredBooks()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Empréstimos") },
                navigationIcon = {
                    IconButton(onClick = onOpenRewards) {
                        Icon(Icons.Default.Star, contentDescription = "Recompensas")
                    }
                },
                actions = {
                    IconButton(onClick = onOpenNotifications) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notificações")
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
            OutlinedTextField(
                value = state.search,
                onValueChange = viewModel::onSearchChange,
                label = { Text("Buscar por título ou autor") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                FilterChip(
                    selected = state.filter == "Todos",
                    onClick = { viewModel.onFilterChange("Todos") },
                    label = { Text("Todos") }
                )
                FilterChip(
                    selected = state.filter == "Ativos",
                    onClick = { viewModel.onFilterChange("Ativos") },
                    label = { Text("Ativos") }
                )
                FilterChip(
                    selected = state.filter == "Concluídos",
                    onClick = { viewModel.onFilterChange("Concluídos") },
                    label = { Text("Concluídos") }
                )
            }

            Spacer(Modifier.height(16.dp))

            LazyColumn {
                items(books) { book ->
                    BookLoanCard(
                        book = book,
                        onClick = { onOpenBook(book.id) }
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun BookLoanCard(book: Book, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(book.title, fontWeight = FontWeight.Bold)
            Text(book.author, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Text("Status: ${book.status}", style = MaterialTheme.typography.bodySmall)
            book.dueDate?.let {
                Text("Devolução: $it", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
