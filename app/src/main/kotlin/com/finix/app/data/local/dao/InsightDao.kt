package com.finix.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.finix.app.data.local.entity.InsightEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InsightDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInsight(insight: InsightEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInsights(insights: List<InsightEntity>)

    @Update
    suspend fun updateInsight(insight: InsightEntity)

    @Delete
    suspend fun deleteInsight(insight: InsightEntity)

    @Query("SELECT * FROM insights WHERE id = :insightId")
    suspend fun getInsightById(insightId: String): InsightEntity?

    @Query("SELECT * FROM insights WHERE userId = :userId AND (expiresAt IS NULL OR expiresAt > :currentTime) ORDER BY createdAt DESC")
    fun getActiveInsightsByUserIdFlow(userId: String, currentTime: Long = System.currentTimeMillis()): Flow<List<InsightEntity>>

    @Query("SELECT * FROM insights WHERE userId = :userId ORDER BY priority DESC, createdAt DESC LIMIT :limit")
    suspend fun getInsightsByUserId(userId: String, limit: Int = 10): List<InsightEntity>

    @Query("DELETE FROM insights WHERE userId = :userId")
    suspend fun deleteUserInsights(userId: String)

    @Query("DELETE FROM insights WHERE expiresAt IS NOT NULL AND expiresAt < :currentTime")
    suspend fun deleteExpiredInsights(currentTime: Long = System.currentTimeMillis())
}
