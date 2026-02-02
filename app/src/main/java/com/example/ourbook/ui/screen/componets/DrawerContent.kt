package com.example.ourbook.ui.screen.componets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    onProfile: () -> Unit,
    onLoans: () -> Unit,
    onRewards: () -> Unit,
    onLogout: () -> Unit
) {
    ModalDrawerSheet {
        Spacer(Modifier.height(24.dp))

        Text(
            text = "OurBook",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        NavigationDrawerItem(
            label = { Text("Meu Perfil") },
            icon = { Icon(Icons.Default.Person, null) },
            selected = false,
            onClick = onProfile
        )

        NavigationDrawerItem(
            label = { Text("Meus Empréstimos") },
            icon = { Icon(Icons.Default.Book, null) },
            selected = false,
            onClick = onLoans
        )

        NavigationDrawerItem(
            label = { Text("Recompensas") },
            icon = { Icon(Icons.Default.Star, null) },
            selected = false,
            onClick = onRewards
        )

        Divider(Modifier.padding(vertical = 8.dp))

        NavigationDrawerItem(
            label = { Text("Sair") },
            icon = {
                Icon(
                    Icons.Default.ExitToApp,
                    null,
                    tint = MaterialTheme.colorScheme.error
                )
            },
            selected = false,
            onClick = onLogout
        )
    }
}
