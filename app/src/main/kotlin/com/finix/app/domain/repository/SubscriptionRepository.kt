package com.finix.app.domain.repository

import com.finix.app.domain.model.Subscription
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    suspend fun addSubscription(subscription: Subscription): Result<Unit>
    suspend fun updateSubscription(subscription: Subscription): Result<Unit>
    suspend fun deleteSubscription(subscriptionId: String): Result<Unit>
    suspend fun getSubscription(subscriptionId: String): Result<Subscription?>
    fun getActiveSubscriptionsByUserId(userId: String): Flow<List<Subscription>>
    suspend fun getSubscriptionsByUserId(userId: String): Result<List<Subscription>>
    suspend fun getTotalMonthlySubscriptions(userId: String): Result<Double>
}
