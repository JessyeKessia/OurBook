package com.example.ourbook.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ourbook.data.model.Book
import kotlinx.coroutines.flow.Flow

@Dao //
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<Book>> //

    @Insert(onConflict = OnConflictStrategy.REPLACE) //
    suspend fun insertBooks(books: List<Book>)
}