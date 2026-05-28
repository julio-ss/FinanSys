package com.finix.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Insight(
    val id: String,
    val userId: String,
    val title: String,
    val message: String,
    val type: InsightType,
    val value: Double? = null,
    val percentage: Double? = null,
    val category: String? = null,
    val priority: InsightPriority = InsightPriority.MEDIUM,
    val icon: String,
    val color: String,
    val actionable: Boolean = false,
    val actionUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long? = null
)

enum class InsightType(val value: String) {
    SPENDING("spending"),
    SAVING("saving"),
    TREND("trend"),
    ALERT("alert"),
    RECOMMENDATION("recommendation")
}

enum class InsightPriority(val value: String) {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high")
}
