package com.finix.app.domain.repository

import com.finix.app.domain.model.Insight
import kotlinx.coroutines.flow.Flow

interface InsightRepository {
    suspend fun addInsight(insight: Insight): Result<Unit>
    suspend fun updateInsight(insight: Insight): Result<Unit>
    suspend fun deleteInsight(insightId: String): Result<Unit>
    fun getActiveInsightsByUserId(userId: String): Flow<List<Insight>>
    suspend fun getInsightsByUserId(userId: String, limit: Int = 10): Result<List<Insight>>
    suspend fun generateAIInsights(userId: String): Result<List<Insight>>
}
