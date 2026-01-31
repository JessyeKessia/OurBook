package com.example.ourbook.data.local

import com.example.ourbook.data.model.*
import androidx.room.*
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.ourbook.data.model.Book

@Dao //
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<Book>> //

    @Insert(onConflict = OnConflictStrategy.REPLACE) //
    suspend fun insertBooks(books: List<Book>)

    @Query("UPDATE books SET isAvailable = :available WHERE id = :bookId")
    suspend fun updateAvailability(bookId: String, available: Boolean)
}