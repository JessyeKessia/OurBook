package com.example.ourbook.data.repository

import com.example.ourbook.data.model.Book
import com.example.ourbook.data.local.BookDao
import com.example.ourbook.data.local.NotificationDao
import com.example.ourbook.data.local.RewardDao
import com.example.ourbook.data.model.RewardItem
import com.example.ourbook.data.local.UserDao
import com.example.ourbook.data.local.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.Flow
import com.example.ourbook.data.model.NotificationItem


class OurBookRepositoryLocal(
    private val userDao: UserDao,
    private val bookDao: BookDao,
    private val notificationDao: NotificationDao,
    private val rewardDao: RewardDao
) {
    fun getBooksFlow(): Flow<List<Book>> = bookDao.getAllBooks()

    suspend fun getBookById(id: String): Book? = bookDao.getAllBooks().first().find { it.id == id }
    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    // ================= USER =================
    suspend fun login(email: String, senha: String): Boolean {
        val user = userDao.validateLogin(email, senha)
        return if (user != null) {
            val loggedUser = user.copy(isLogged = true)
            userDao.insertUser(loggedUser)
            _currentUser.value = loggedUser
            true
        } else false
    }

    suspend fun logout() {
        _currentUser.value?.let {
            userDao.insertUser(it.copy(isLogged = false))
        }
        _currentUser.value = null
    }

    // ================= BOOKS (Agora lendo do SQLite) =================
    suspend fun getBooks(): List<Book> {
        return bookDao.getAllBooks().first() // Pega a lista do banco local
    }

    suspend fun registerLoan(bookId: String, dueDate: String) {
        bookDao.updateAvailability(bookId, false)
    }

    // ================= REWARDS (Agora lendo do SQLite) =================
    suspend fun getRewards(): List<RewardItem> {
        return rewardDao.getAllRewards().first()
    }

    suspend fun getNotifications(): List<NotificationItem> {
        // Como o DAO retorna um Flow, .first() para pegar a lista atual
        return notificationDao.getAllNotifications().first()
    }

    suspend fun redeemReward(rewardId: String, cost: Int) {
        val user = _currentUser.value ?: return
        if (user.coins >= cost) {
            val updatedUser = user.copy(coins = user.coins - cost)
            userDao.insertUser(updatedUser)
            _currentUser.value = updatedUser
        }
    }
}