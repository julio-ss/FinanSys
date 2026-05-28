package com.finix.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.finix.app.data.local.entity.SubscriptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscription(subscription: SubscriptionEntity)

    @Update
    suspend fun updateSubscription(subscription: SubscriptionEntity)

    @Delete
    suspend fun deleteSubscription(subscription: SubscriptionEntity)

    @Query("SELECT * FROM subscriptions WHERE id = :subscriptionId")
    suspend fun getSubscriptionById(subscriptionId: String): SubscriptionEntity?

    @Query("SELECT * FROM subscriptions WHERE userId = :userId AND isActive = 1 ORDER BY nextPaymentDate")
    fun getActiveSubscriptionsByUserIdFlow(userId: String): Flow<List<SubscriptionEntity>>

    @Query("SELECT * FROM subscriptions WHERE userId = :userId ORDER BY nextPaymentDate")
    suspend fun getSubscriptionsByUserId(userId: String): List<SubscriptionEntity>

    @Query("DELETE FROM subscriptions WHERE userId = :userId")
    suspend fun deleteUserSubscriptions(userId: String)

    @Query("SELECT SUM(amount) FROM subscriptions WHERE userId = :userId AND isActive = 1")
    suspend fun getTotalMonthlySubscriptions(userId: String): Double?
}
