package com.example.ourbook.data

import com.example.ourbook.data.model.Book
import com.example.ourbook.data.model.NotificationItem
import com.example.ourbook.data.model.RewardItem
import com.example.ourbook.data.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OurBookRepository {

    private val _currentUser = MutableStateFlow<User?>(User(
        id = "1",
        name = "João Silva",
        email = "joao@ourbook.com",
        coins = 120
    ))
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _books = MutableStateFlow(
        listOf(
            Book("1", "O Pequeno Príncipe", "Antoine de Saint-Exupéry", "Emprestado", "02/12/2025"),
            Book("2", "1984", "George Orwell", "Atrasado", "25/11/2025"),
            Book("3", "A Culpa é das Estrelas", "John Green", "Devolvido"),
            Book("4", "Dom Casmurro", "Machado de Assis", "Disponível")
        )
    )
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    private val _notifications = MutableStateFlow(
        listOf(
            NotificationItem(
                "1", "prazo",
                "Prazo de Devolução Próximo",
                "Devolva 'O Pequeno Príncipe' até amanhã.",
                "2 horas atrás"
            ),
            NotificationItem(
                "2", "recompensa",
                "Você ganhou 10 moedas!",
                "Parabéns por devolver 'A Culpa é das Estrelas' no prazo.",
                "1 dia atrás"
            ),
            NotificationItem(
                "3", "atraso",
                "Livro Atrasado",
                "Seu empréstimo de '1984' está atrasado. Por favor, devolva o quanto antes.",
                "3 dias atrás"
            )
        )
    )
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _rewards = MutableStateFlow(
        listOf(
            RewardItem("1", "Desconto em multa", "Pague 50% a menos em multas de atraso.", 50),
            RewardItem("2", "Brinde da biblioteca", "Ganhe um marcador de página exclusivo.", 30),
            RewardItem("3", "Acesso antecipado", "Veja novos livros 3 dias antes de todos.", 70)
        )
    )
    val rewards: StateFlow<List<RewardItem>> = _rewards.asStateFlow()

    suspend fun login(email: String, password: String): Boolean {
        delay(500)
        _currentUser.update {
            it?.copy(email = email) ?: User("1", "João Silva", email, 120)
        }
        return true
    }

    suspend fun registerLoan(bookId: String, userName: String, dueDate: String) {
        delay(500)
        _books.update { list ->
            list.map {
                if (it.id == bookId) it.copy(status = "Emprestado", dueDate = dueDate) else it
            }
        }
    }

    suspend fun redeemReward(rewardId: String) {
        delay(500)
        val reward = _rewards.value.firstOrNull { it.id == rewardId } ?: return
        _currentUser.update { user ->
            user?.copy(coins = (user.coins - reward.costCoins).coerceAtLeast(0))
        }
    }
}
