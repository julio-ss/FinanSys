package com.finix.app.di

import android.content.Context
import com.finix.app.data.local.FinixDatabase
import com.finix.app.data.local.dao.AccountDao
import com.finix.app.data.local.dao.CardDao
import com.finix.app.data.local.dao.CategoryDao
import com.finix.app.data.local.dao.GoalDao
import com.finix.app.data.local.dao.InsightDao
import com.finix.app.data.local.dao.NotificationDao
import com.finix.app.data.local.dao.SubscriptionDao
import com.finix.app.data.local.dao.TransactionDao
import com.finix.app.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFinixDatabase(
        @ApplicationContext context: Context
    ): FinixDatabase = FinixDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideUserDao(database: FinixDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideTransactionDao(database: FinixDatabase): TransactionDao = database.transactionDao()

    @Provides
    @Singleton
    fun provideCategoryDao(database: FinixDatabase): CategoryDao = database.categoryDao()

    @Provides
    @Singleton
    fun provideCardDao(database: FinixDatabase): CardDao = database.cardDao()

    @Provides
    @Singleton
    fun provideAccountDao(database: FinixDatabase): AccountDao = database.accountDao()

    @Provides
    @Singleton
    fun provideGoalDao(database: FinixDatabase): GoalDao = database.goalDao()

    @Provides
    @Singleton
    fun provideSubscriptionDao(database: FinixDatabase): SubscriptionDao = database.subscriptionDao()

    @Provides
    @Singleton
    fun provideNotificationDao(database: FinixDatabase): NotificationDao = database.notificationDao()

    @Provides
    @Singleton
    fun provideInsightDao(database: FinixDatabase): InsightDao = database.insightDao()
}
