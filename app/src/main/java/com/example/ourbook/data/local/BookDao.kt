package com.example.ourbook.data.local

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

    @Query("""
    UPDATE books 
    SET isAvailable = :available,
        loanUserId = :loanUserId,
        dueDate = :dueDate
    WHERE id = :bookId""")
    suspend fun updateLoan(
        bookId: Long,
        available: Boolean,
        loanUserId: Long?,
        dueDate: String?
    )

    @Query("""
    SELECT * FROM books WHERE isAvailable = 0 AND loanUserId = :userId """)
    suspend fun getLoansByUser(userId: Long): List<Book>
}