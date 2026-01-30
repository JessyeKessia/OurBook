package com.example.ourbook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ourbook.data.model.*

@Database(
    entities = [
        Book::class,
        NotificationItem::class,
        RewardItem::class,
        User::class
    ],
    version = 1,
    exportSchema = false
)
abstract class OurBookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun notificationDao(): NotificationDao
    abstract fun rewardDao(): RewardDao
    abstract fun userDao(): UserDao
}