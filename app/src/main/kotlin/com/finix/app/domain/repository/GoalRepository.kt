package com.finix.app.domain.repository

import com.finix.app.domain.model.Goal
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    suspend fun addGoal(goal: Goal): Result<Unit>
    suspend fun updateGoal(goal: Goal): Result<Unit>
    suspend fun deleteGoal(goalId: String): Result<Unit>
    suspend fun getGoal(goalId: String): Result<Goal?>
    fun getActiveGoalsByUserId(userId: String): Flow<List<Goal>>
    suspend fun getGoalsByUserId(userId: String): Result<List<Goal>>
}
