package com.finix.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(
    tableName = "transactions",
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
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val categoryId: String,
    val accountId: String,
    val amount: Double,
    val description: String,
    val type: String, // "income" or "expense" or "transfer"
    val date: Long,
    val tags: String = "", // JSON string
    val receiptUrl: String? = null,
    val isRecurring: Boolean = false,
    val recurrenceType: String? = null, // "daily", "weekly", "monthly", "yearly"
    val syncedWithCloud: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
