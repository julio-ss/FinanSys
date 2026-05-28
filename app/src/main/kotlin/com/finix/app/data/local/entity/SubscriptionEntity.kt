package com.finix.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(
    tableName = "subscriptions",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Serializable
data class SubscriptionEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val categoryId: String,
    val name: String,
    val amount: Double,
    val frequency: String, // "daily", "weekly", "monthly", "yearly"
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
