package com.finix.app.data.repository

import com.finix.app.data.local.dao.GoalDao
import com.finix.app.data.local.entity.GoalEntity
import com.finix.app.domain.model.Goal
import com.finix.app.domain.model.GoalPriority
import com.finix.app.domain.model.GoalStatus
import com.finix.app.domain.repository.GoalRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoalRepositoryImpl @Inject constructor(
    private val goalDao: GoalDao,
    private val firestore: FirebaseFirestore
) : GoalRepository {

    override suspend fun addGoal(goal: Goal): Result<Unit> = try {
        goalDao.insertGoal(goal.toEntity())
        firestore.collection("goals").document(goal.id).set(goal.toEntity()).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateGoal(goal: Goal): Result<Unit> = try {
        goalDao.updateGoal(goal.toEntity())
        firestore.collection("goals").document(goal.id).set(goal.toEntity()).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteGoal(goalId: String): Result<Unit> = try {
        goalDao.deleteGoal(goalDao.getGoalById(goalId) ?: throw Exception())
        firestore.collection("goals").document(goalId).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getGoal(goalId: String): Result<Goal?> = try {
        Result.success(goalDao.getGoalById(goalId)?.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getActiveGoalsByUserId(userId: String): Flow<List<Goal>> =
        goalDao.getActiveGoalsByUserIdFlow(userId).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun getGoalsByUserId(userId: String): Result<List<Goal>> = try {
        Result.success(goalDao.getGoalsByUserId(userId).map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun Goal.toEntity() = GoalEntity(
        id, userId, name, description, targetAmount, currentAmount, deadline,
        category, icon, color, status.value, priority.value, updatedAt = System.currentTimeMillis()
    )

    private fun GoalEntity.toDomain() = Goal(
        id, userId, name, description, targetAmount, currentAmount, deadline,
        category, icon, color,
        GoalStatus.values().first { it.value == status },
        GoalPriority.values().first { it.value == priority },
        createdAt, updatedAt
    )
}
