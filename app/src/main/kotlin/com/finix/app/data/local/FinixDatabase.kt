package com.finix.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.finix.app.data.local.dao.AccountDao
import com.finix.app.data.local.dao.CardDao
import com.finix.app.data.local.dao.CategoryDao
import com.finix.app.data.local.dao.GoalDao
import com.finix.app.data.local.dao.InsightDao
import com.finix.app.data.local.dao.NotificationDao
import com.finix.app.data.local.dao.SubscriptionDao
import com.finix.app.data.local.dao.TransactionDao
import com.finix.app.data.local.dao.UserDao
import com.finix.app.data.local.entity.AccountEntity
import com.finix.app.data.local.entity.CardEntity
import com.finix.app.data.local.entity.CategoryEntity
import com.finix.app.data.local.entity.GoalEntity
import com.finix.app.data.local.entity.InsightEntity
import com.finix.app.data.local.entity.NotificationEntity
import com.finix.app.data.local.entity.SubscriptionEntity
import com.finix.app.data.local.entity.TransactionEntity
import com.finix.app.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        TransactionEntity::class,
        CategoryEntity::class,
        CardEntity::class,
        AccountEntity::class,
        GoalEntity::class,
        SubscriptionEntity::class,
        NotificationEntity::class,
        InsightEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FinixDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun cardDao(): CardDao
    abstract fun accountDao(): AccountDao
    abstract fun goalDao(): GoalDao
    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun notificationDao(): NotificationDao
    abstract fun insightDao(): InsightDao

    companion object {
        @Volatile
        private var INSTANCE: FinixDatabase? = null

        fun getInstance(context: Context): FinixDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FinixDatabase::class.java,
                    "finix_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
