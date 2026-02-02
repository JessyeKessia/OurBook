package com.example.ourbook.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ourbook.data.model.Book
import com.example.ourbook.ui.screen.componets.DrawerContent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onOpenBook: (Long) -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenRewards: () -> Unit,
    onOpenMyLoans: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
    onLogout: () -> Unit = { viewModel.logout() }
) {
    val state by viewModel.uiState.collectAsState()
    val books by viewModel.filteredBooks.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onProfile = {
                    onOpenProfile()
                    scope.launch { drawerState.close() }
                },
                onLoans = {
                    onOpenMyLoans()
                    scope.launch { drawerState.close() }
                },
                onRewards = {
                    onOpenRewards()
                    scope.launch { drawerState.close() }
                },
                onLogout = {
                    scope.launch {
                        // Logout real
                        onLogout() // Se for suspend, já é executado aqui

                        // Fecha o drawer
                        drawerState.close()
                    }
                }

            )
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Empréstimos") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
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

                // Campo de busca
                OutlinedTextField(
                    value = state.search,
                    onValueChange = viewModel::onSearchChange,
                    label = { Text("Buscar por título ou autor") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                // Filtros
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
                        selected = state.filter == "Disponíveis",
                        onClick = { viewModel.onFilterChange("Disponíveis") },
                        label = { Text("Disponíveis") }
                    )
                    FilterChip(
                        selected = state.filter == "Emprestados",
                        onClick = { viewModel.onFilterChange("Emprestados") },
                        label = { Text("Emprestados") }
                    )
                }

                Spacer(Modifier.height(12.dp))

                // Lista de livros
                LazyColumn {
                    items(books) { book ->
                        BookLoanCard(
                            book = book,
                            onClick = { onOpenBook(book.id) } // abre o detalhe do livro
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}
