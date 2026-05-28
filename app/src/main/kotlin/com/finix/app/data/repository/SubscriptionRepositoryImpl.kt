package com.finix.app.data.repository

import com.finix.app.data.local.dao.SubscriptionDao
import com.finix.app.data.local.entity.SubscriptionEntity
import com.finix.app.domain.model.Subscription
import com.finix.app.domain.model.SubscriptionFrequency
import com.finix.app.domain.repository.SubscriptionRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SubscriptionRepositoryImpl @Inject constructor(
    private val subscriptionDao: SubscriptionDao,
    private val firestore: FirebaseFirestore
) : SubscriptionRepository {

    override suspend fun addSubscription(subscription: Subscription): Result<Unit> = try {
        subscriptionDao.insertSubscription(subscription.toEntity())
        firestore.collection("subscriptions").document(subscription.id).set(subscription.toEntity()).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateSubscription(subscription: Subscription): Result<Unit> = try {
        subscriptionDao.updateSubscription(subscription.toEntity())
        firestore.collection("subscriptions").document(subscription.id).set(subscription.toEntity()).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteSubscription(subscriptionId: String): Result<Unit> = try {
        subscriptionDao.deleteSubscription(subscriptionDao.getSubscriptionById(subscriptionId) ?: throw Exception())
        firestore.collection("subscriptions").document(subscriptionId).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getSubscription(subscriptionId: String): Result<Subscription?> = try {
        Result.success(subscriptionDao.getSubscriptionById(subscriptionId)?.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getActiveSubscriptionsByUserId(userId: String): Flow<List<Subscription>> =
        subscriptionDao.getActiveSubscriptionsByUserIdFlow(userId).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun getSubscriptionsByUserId(userId: String): Result<List<Subscription>> = try {
        Result.success(subscriptionDao.getSubscriptionsByUserId(userId).map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getTotalMonthlySubscriptions(userId: String): Result<Double> = try {
        Result.success(subscriptionDao.getTotalMonthlySubscriptions(userId) ?: 0.0)
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun Subscription.toEntity() = SubscriptionEntity(
        id, userId, categoryId, name, amount, frequency.value, nextPaymentDate,
        lastPaymentDate, daysUntilRenewal, isActive, icon, color, notes, updatedAt = System.currentTimeMillis()
    )

    private fun SubscriptionEntity.toDomain() = Subscription(
        id, userId, categoryId, name, amount,
        SubscriptionFrequency.values().first { it.value == frequency },
        nextPaymentDate, lastPaymentDate, daysUntilRenewal, isActive, icon, color, notes, createdAt, updatedAt
    )
}
