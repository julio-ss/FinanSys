package com.finix.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Goal(
    val id: String,
    val userId: String,
    val name: String,
    val description: String? = null,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val deadline: Long,
    val category: String,
    val icon: String,
    val color: String,
    val status: GoalStatus = GoalStatus.ACTIVE,
    val priority: GoalPriority = GoalPriority.MEDIUM,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    val progress: Float get() = (currentAmount / targetAmount).coerceIn(0f, 1f)
    val daysRemaining: Long get() = (deadline - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)
}

enum class GoalStatus(val value: String) {
    ACTIVE("active"),
    COMPLETED("completed"),
    ABANDONED("abandoned")
}

enum class GoalPriority(val value: String) {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high")
}
