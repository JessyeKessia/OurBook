package com.example.ourbook.data.repository

import android.net.Uri
import com.example.ourbook.data.model.Book
import com.example.ourbook.data.local.BookDao
import com.example.ourbook.data.local.LoanDao
import com.example.ourbook.data.local.NotificationDao
import com.example.ourbook.data.local.RewardDao
import com.example.ourbook.data.model.RewardItem
import com.example.ourbook.data.local.UserDao
import com.example.ourbook.data.local.UserEntity
import com.example.ourbook.data.model.LoanEntity
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
    private val rewardDao: RewardDao,
    private val loanDao: LoanDao

) {
    fun getBooksFlow(): Flow<List<Book>> = bookDao.getAllBooks()

    suspend fun getBookById(id: Long): Book? = bookDao.getAllBooks().first().find { it.id == id }
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
    suspend fun registerUser(
        name: String,
        email: String,
        senha: String
    ): Boolean {

        // verifica se e-mail já existe
        val existingUser = userDao.getUserByEmail(email)
        if (existingUser != null) return false

        val newUser = UserEntity(
            name = name,
            email = email,
            senha = senha,
            coins = 0,
            isLogged = false
        )

        userDao.insertUser(newUser)
        return true
    }

    suspend fun loadLoggedUser() {
        val user = userDao.getLoggedUser()
        _currentUser.value = user
    }

    suspend fun updateUserPhoto(uri: Uri) {
        val user = _currentUser.value ?: return

        val updatedUser = user.copy(
            photoUri = uri.toString()
        )

        userDao.insertUser(updatedUser)
        _currentUser.value = updatedUser
    }

    // ================= BOOKS (Agora lendo do SQLite) =================
    suspend fun getBooks(): List<Book> {
        return bookDao.getAllBooks().first() // Pega a lista do banco local
    }

    suspend fun registerLoan(bookId: Long, dueDate: String) {
        val user = _currentUser.value ?: return

        loanDao.insertLoan(
            LoanEntity(
                bookId = bookId,
                userId = user.id,
                dueDate = dueDate
            )
        )

        bookDao.updateLoan(bookId, false, user.id, dueDate)
    }
    suspend fun getLoansByUser(userId: Long): List<Book> {
        return bookDao.getLoansByUser(userId)
    }

    suspend fun returnLoan(bookId: Long) {
        val user = _currentUser.value ?: return

        bookDao.updateLoan(
            bookId = bookId,
            available = true,
            loanUserId = null,
            dueDate = null
        )
        val updatedUser = user.copy(coins = user.coins + 10)

        userDao.insertUser(updatedUser)

        _currentUser.value = updatedUser
    }

    // ================= REWARDS (Agora lendo do SQLite) =================
    suspend fun getRewards(): List<RewardItem> {
        return rewardDao.getAllRewards().first()
    }

    suspend fun getNotifications(): List<NotificationItem> {
        // Como o DAO retorna um Flow, .first() para pegar a lista atual
        return notificationDao.getAllNotifications().first()
    }

    suspend fun redeemReward(rewardId: Long, cost: Int) {
        val user = _currentUser.value ?: return
        if (user.coins >= cost) {
            val updatedUser = user.copy(coins = user.coins - cost)
            userDao.insertUser(updatedUser)
            _currentUser.value = updatedUser
        }
    }
}