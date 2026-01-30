package com.example.ourbook.data.remote;
import com.example.ourbook.data.model.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

object OurBookRepositoryRemote {

    private val firestore = FirebaseFirestore.getInstance()

    // ================= USER =================

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    suspend fun login(email: String, senha: String): Boolean {
        val result = firestore.collection("users")
            .whereEqualTo("email", email)
            .whereEqualTo("senha", senha)
            .get()
            .await()

        if (result.isEmpty) return false

        val doc = result.documents.first()

        val user = User(
            id = doc.id,
            name = doc.getString("name") ?: "",
            email = doc.getString("email") ?: "",
            senha = doc.getString("senha") ?: "",
            coins = doc.getLong("coins")?.toInt() ?: 0
        )

        firestore.collection("users")
            .document(user.id)
            .update("isLogged", true)
            .await()

        _currentUser.value = user
        return true
    }

    suspend fun logout() {
        val user = _currentUser.value ?: return

        firestore.collection("users")
            .document(user.id)
            .update("isLogged", false)
            .await()

        _currentUser.value = null
    }

    suspend fun restoreSession(): Boolean {
        val result = firestore.collection("users")
            .whereEqualTo("isLogged", true)
            .get()
            .await()

        if (result.isEmpty) return false

        val doc = result.documents.first()

        _currentUser.value = User(
            id = doc.id,
            name = doc.getString("name") ?: "",
            email = doc.getString("email") ?: "",
            senha = doc.getString("senha") ?: "",
            coins = doc.getLong("coins")?.toInt() ?: 0
        )

        return true
    }

    // ================= BOOKS =================

    suspend fun getBooks(): List<Book> {
        return firestore.collection("books")
            .get()
            .await()
            .documents
            .map {
                Book(
                    id = it.id,
                    title = it.getString("title") ?: "",
                    author = it.getString("author") ?: "",
                    isAvailable = it.getBoolean("isAvailable") ?: true,
                    dueDate = it.getString("dueDate"),
                    coverUrl = it.getString("coverUrl")
                )
            }
    }

    suspend fun getBookById(id: String): Book? {
        val doc = firestore.collection("books")
            .document(id)
            .get()
            .await()

        return if (doc.exists()) {
            Book(
                id = doc.id,
                title = doc.getString("title") ?: "",
                author = doc.getString("author") ?: "",
                isAvailable = doc.getBoolean("isAvailable") ?: true,
                dueDate = doc.getString("dueDate"),
                coverUrl = doc.getString("coverUrl")
            )
        } else null
    }

    // registra um emprestimo no banco de dados
    suspend fun registerLoan(bookId: String, dueDate: String) {
        firestore.collection("books")
            .document(bookId)
            .update(
                mapOf(
                    "isAvailable" to false,
                    "dueDate" to dueDate
                )
            )
            .await()
    }

    // ================= REWARDS =================

    suspend fun getRewards(): List<RewardItem> {
        return firestore.collection("rewards")
            .get()
            .await()
            .documents
            .map {
                RewardItem(
                    id = it.id,
                    title = it.getString("title") ?: "",
                    description = it.getString("description") ?: "",
                    costCoins = it.getLong("costCoins")?.toInt() ?: 0
                )
            }
    }

    suspend fun redeemReward(rewardId: String) {
        val user = _currentUser.value ?: return

        val rewardDoc = firestore.collection("rewards")
            .document(rewardId)
            .get()
            .await()

        val costLong  = rewardDoc.getLong("costCoins") ?: return
        val cost = costLong.toInt()

        if (user.coins < cost) return

        firestore.collection("users")
            .document(user.id)
            .update("coins", user.coins - cost)
            .await()

        _currentUser.value = user.copy(coins = user.coins - cost)
    }

    // ================= NOTIFICATIONS =================

    suspend fun getNotifications(): List<NotificationItem> {
        return firestore.collection("notifications")
            .get()
            .await()
            .documents
            .map {
                NotificationItem(
                    id = it.id,
                    type = it.getString("type") ?: "",
                    title = it.getString("title") ?: "",
                    message = it.getString("message") ?: "",
                    timeAgo = it.getString("timeAgo") ?: ""
                )
            }
    }
}