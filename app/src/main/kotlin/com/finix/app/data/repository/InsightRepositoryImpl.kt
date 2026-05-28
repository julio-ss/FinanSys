package com.finix.app.data.repository

import com.finix.app.data.local.dao.InsightDao
import com.finix.app.data.local.entity.InsightEntity
import com.finix.app.domain.model.Insight
import com.finix.app.domain.model.InsightPriority
import com.finix.app.domain.model.InsightType
import com.finix.app.domain.repository.InsightRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class InsightRepositoryImpl @Inject constructor(
    private val insightDao: InsightDao,
    private val firestore: FirebaseFirestore
) : InsightRepository {

    override suspend fun addInsight(insight: Insight): Result<Unit> = try {
        insightDao.insertInsight(insight.toEntity())
        firestore.collection("insights").document(insight.id).set(insight.toEntity()).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateInsight(insight: Insight): Result<Unit> = try {
        insightDao.updateInsight(insight.toEntity())
        firestore.collection("insights").document(insight.id).set(insight.toEntity()).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteInsight(insightId: String): Result<Unit> = try {
        insightDao.deleteInsight(insightDao.getInsightById(insightId) ?: throw Exception())
        firestore.collection("insights").document(insightId).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getActiveInsightsByUserId(userId: String): Flow<List<Insight>> =
        insightDao.getActiveInsightsByUserIdFlow(userId).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun getInsightsByUserId(userId: String, limit: Int): Result<List<Insight>> = try {
        Result.success(insightDao.getInsightsByUserId(userId, limit).map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun generateAIInsights(userId: String): Result<List<Insight>> = try {
        val insights = listOf(
            Insight(
                id = UUID.randomUUID().toString(),
                userId = userId,
                title = "Parabéns!",
                message = "Você economizou 15% mais que o mês anterior",
                type = InsightType.SAVING,
                percentage = 15.0,
                priority = InsightPriority.HIGH,
                icon = "🎉",
                color = "#10B981",
                createdAt = System.currentTimeMillis()
            )
        )
        insights.forEach { addInsight(it).getOrNull() }
        Result.success(insights)
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun Insight.toEntity() = InsightEntity(
        id, userId, title, message, type.value, value, percentage, category,
        priority.value, icon, color, actionable, actionUrl, createdAt, expiresAt
    )

    private fun InsightEntity.toDomain() = Insight(
        id, userId, title, message, InsightType.values().first { it.value == type },
        value, percentage, category, InsightPriority.values().first { it.value == priority },
        icon, color, actionable, actionUrl, createdAt, expiresAt
    )
}
