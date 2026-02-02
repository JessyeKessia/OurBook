package com.example.ourbook.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ourbook.data.model.Book
import com.example.ourbook.data.model.LoanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoanDao {

    @Insert
    suspend fun insertLoan(loan: LoanEntity)

    @Query("""
        SELECT books.* FROM books
        INNER JOIN loans ON books.id = loans.bookId
        WHERE loans.userId = :id
        AND loans.returned = 0
    """)
    fun getLoansByUser(id: Long): Flow<List<Book>>

    @Query("""
        UPDATE loans
        SET returned = 1,
            returnPhotoUri = :photoUri
        WHERE bookId = :bookId
        AND returned = 0
    """)
    suspend fun returnBook(bookId: String, photoUri: String)
}
