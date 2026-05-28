package com.finix.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.finix.app.data.local.entity.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: GoalEntity)

    @Update
    suspend fun updateGoal(goal: GoalEntity)

    @Delete
    suspend fun deleteGoal(goal: GoalEntity)

    @Query("SELECT * FROM goals WHERE id = :goalId")
    suspend fun getGoalById(goalId: String): GoalEntity?

    @Query("SELECT * FROM goals WHERE userId = :userId AND status = 'active' ORDER BY deadline")
    fun getActiveGoalsByUserIdFlow(userId: String): Flow<List<GoalEntity>>

    @Query("SELECT * FROM goals WHERE userId = :userId ORDER BY deadline")
    suspend fun getGoalsByUserId(userId: String): List<GoalEntity>

    @Query("DELETE FROM goals WHERE userId = :userId")
    suspend fun deleteUserGoals(userId: String)
}
