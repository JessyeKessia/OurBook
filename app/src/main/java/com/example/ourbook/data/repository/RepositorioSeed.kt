package com.example.ourbook.data.repository

import android.util.Log
import com.example.ourbook.data.model.Book
import com.example.ourbook.data.model.NotificationItem
import com.example.ourbook.data.model.RewardItem
import com.example.ourbook.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RepositorioSeed {

    private val firestore = FirebaseFirestore.getInstance()

    // ================= USER =================
    suspend fun seedUsers() {
        val users = listOf(
            User(id = "1", name = "João Silva", email = "joao@ourbook.com", senha = "123456", coins = 120),
            User(id = "2", name = "Maria Souza", email = "maria@ourbook.com", senha = "123456", coins = 80)
        )

        users.forEach { user ->
            try {
                firestore.collection("users").document(user.id).set(user).await()
                Log.d("SeedData", "Usuário ${user.name} cadastrado!")
            } catch (e: Exception) {
                Log.e("SeedData", "Erro ao cadastrar usuário ${user.name}: $e")
            }
        }
    }

    // ================= BOOKS =================
    suspend fun seedBooks() {
        val books = listOf(
            Book(id = "1", title = "O Pequeno Príncipe", author = "Antoine de Saint-Exupéry"),
            Book(id = "2", title = "1984", author = "George Orwell"),
            Book(id = "3", title = "A Culpa é das Estrelas", author = "John Green"),
            Book(id = "4", title = "Dom Casmurro", author = "Machado de Assis")
        )

        books.forEach { book ->
            try {
                firestore.collection("books").document(book.id).set(book).await()
                Log.d("SeedData", "Livro ${book.title} cadastrado!")
            } catch (e: Exception) {
                Log.e("SeedData", "Erro ao cadastrar livro ${book.title}: $e")
            }
        }
    }

    // ================= REWARDS =================
    suspend fun seedRewards() {
        val rewards = listOf(
            RewardItem(id = "1", title = "Desconto em multa", description = "Pague 50% a menos em multas de atraso.", costCoins = 50),
            RewardItem(id = "2", title = "Brinde da biblioteca", description = "Ganhe um marcador de página exclusivo.", costCoins = 30),
            RewardItem(id = "3", title = "Acesso antecipado", description = "Veja novos livros 3 dias antes de todos.", costCoins = 70)
        )

        rewards.forEach { reward ->
            try {
                firestore.collection("rewards").document(reward.id).set(reward).await()
                Log.d("SeedData", "Recompensa ${reward.title} cadastrada!")
            } catch (e: Exception) {
                Log.e("SeedData", "Erro ao cadastrar recompensa ${reward.title}: $e")
            }
        }
    }

    // ================= NOTIFICATIONS =================
    suspend fun seedNotifications() {
        val notifications = listOf(
            NotificationItem(id = "1", type = "prazo", title = "Prazo de Devolução Próximo", message = "Devolva 'O Pequeno Príncipe' até amanhã.", timeAgo = "2 horas atrás"),
            NotificationItem(id = "2", type = "recompensa", title = "Você ganhou 10 moedas!", message = "Parabéns por devolver 'A Culpa é das Estrelas' no prazo.", timeAgo = "1 dia atrás"),
            NotificationItem(id = "3", type = "atraso", title = "Livro Atrasado", message = "Seu empréstimo de '1984' está atrasado. Por favor, devolva o quanto antes.", timeAgo = "3 dias atrás")
        )

        notifications.forEach { n ->
            try {
                firestore.collection("notifications").document(n.id).set(n).await()
                Log.d("SeedData", "Notificação ${n.title} cadastrada!")
            } catch (e: Exception) {
                Log.e("SeedData", "Erro ao cadastrar notificação ${n.title}: $e")
            }
        }
    }

    // ================= FUNÇÃO ÚNICA =================
    suspend fun seedAll() {
        seedUsers()
        seedBooks()
        seedRewards()
        seedNotifications()
    }
}
