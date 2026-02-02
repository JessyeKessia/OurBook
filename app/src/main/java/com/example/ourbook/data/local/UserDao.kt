package com.example.ourbook.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.ourbook.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): Flow<UserEntity?>

    @Upsert
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun deleteUser()

    // 1. Para o LOGIN: Procura se existe um e-mail e senha iguais no banco
    @Query("SELECT * FROM users WHERE email = :email AND senha = :senha LIMIT 1")
    suspend fun validateLogin(email: String, senha: String): UserEntity?

    // 2. Para o CADASTRO: Verifica se o e-mail já existe antes de criar conta
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE isLogged = 1 LIMIT 1")
    suspend fun getLoggedUser(): UserEntity?
}