package com.finix.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Subscription(
    val id: String,
    val userId: String,
    val categoryId: String,
    val name: String,
    val amount: Double,
    val frequency: SubscriptionFrequency,
    val nextPaymentDate: Long,
    val lastPaymentDate: Long? = null,
    val daysUntilRenewal: Int,
    val isActive: Boolean = true,
    val icon: String,
    val color: String,
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class SubscriptionFrequency(val value: String) {
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly"),
    YEARLY("yearly")
}
