package com.finix.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(
    tableName = "insights",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Serializable
data class InsightEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val title: String,
    val message: String,
    val type: String, // "spending", "saving", "trend", "alert", "recommendation"
    val value: Double? = null,
    val percentage: Double? = null,
    val category: String? = null,
    val priority: String = "medium", // "low", "medium", "high"
    val icon: String,
    val color: String,
    val actionable: Boolean = false,
    val actionUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long? = null
)
