package com.example.ourbook.data.repository

import android.util.Log
import com.example.ourbook.data.local.*
import com.example.ourbook.data.model.*

class RepositorioSeed(
    private val userDao: UserDao,
    private val bookDao: BookDao,
    private val rewardDao: RewardDao,
    private val notificationDao: NotificationDao
) {

    // ================= USER (Aqui você coloca os seus dados!) =================
    suspend fun seedUsers() {
        val users = listOf(
            UserEntity(id = 1, name = "Maira Larissa", email = "maira@ourbook.com", senha = "123", coins = 800),
            UserEntity(id = 2, name = "João Silva", email = "joao@ourbook.com", senha = "123", coins = 120)
        )

        users.forEach { user ->
            userDao.insertUser(user) // Salva direto no seu celular
            Log.d("SeedData", "Usuário ${user.name} cadastrado localmente!")
        }
    }

    // ================= BOOKS =================
    suspend fun seedBooks() {
        val books = listOf(
            Book(id = "1", title = "O Pequeno Príncipe", author = "Antoine de Saint-Exupéry"),
            Book(id = "2", title = "1984", author = "George Orwell"),
            Book(id = "3", title = "Dom Casmurro", author = "Machado de Assis")
        )
        bookDao.insertBooks(books) // Salva no SQLite
    }

    // ================= REWARDS =================
    suspend fun seedRewards() {
        val rewards = listOf(
            RewardItem(id = "1", title = "Desconto em multa", description = "Pague 50% a menos", costCoins = 50),
            RewardItem(id = "2", title = "Brinde da biblioteca", description = "Marcador exclusivo", costCoins = 30)
        )
        rewardDao.insertRewards(rewards) //
    }

    suspend fun seedAll() {
        seedUsers()
        seedBooks()
        seedRewards()
    }
}