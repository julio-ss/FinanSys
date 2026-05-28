package com.finix.app.di

import com.finix.app.data.repository.AccountRepositoryImpl
import com.finix.app.data.repository.CardRepositoryImpl
import com.finix.app.data.repository.CategoryRepositoryImpl
import com.finix.app.data.repository.DashboardRepositoryImpl
import com.finix.app.data.repository.GoalRepositoryImpl
import com.finix.app.data.repository.InsightRepositoryImpl
import com.finix.app.data.repository.NotificationRepositoryImpl
import com.finix.app.data.repository.SubscriptionRepositoryImpl
import com.finix.app.data.repository.TransactionRepositoryImpl
import com.finix.app.data.repository.UserRepositoryImpl
import com.finix.app.domain.repository.AccountRepository
import com.finix.app.domain.repository.CardRepository
import com.finix.app.domain.repository.CategoryRepository
import com.finix.app.domain.repository.DashboardRepository
import com.finix.app.domain.repository.GoalRepository
import com.finix.app.domain.repository.InsightRepository
import com.finix.app.domain.repository.NotificationRepository
import com.finix.app.domain.repository.SubscriptionRepository
import com.finix.app.domain.repository.TransactionRepository
import com.finix.app.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindCardRepository(impl: CardRepositoryImpl): CardRepository

    @Binds
    @Singleton
    abstract fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    abstract fun bindGoalRepository(impl: GoalRepositoryImpl): GoalRepository

    @Binds
    @Singleton
    abstract fun bindSubscriptionRepository(impl: SubscriptionRepositoryImpl): SubscriptionRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(impl: NotificationRepositoryImpl): NotificationRepository

    @Binds
    @Singleton
    abstract fun bindInsightRepository(impl: InsightRepositoryImpl): InsightRepository

    @Binds
    @Singleton
    abstract fun bindDashboardRepository(impl: DashboardRepositoryImpl): DashboardRepository
}
