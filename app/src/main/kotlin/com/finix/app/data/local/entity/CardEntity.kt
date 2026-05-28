package com.finix.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(
    tableName = "cards",
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
data class CardEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val name: String,
    val cardNumber: String,
    val cardHolder: String,
    val expiryDate: String,
    val type: String, // "credit" or "debit"
    val limit: Double? = null,
    val color: String,
    val issuer: String, // "visa", "mastercard", "amex", etc.
    val closingDay: Int = 10,
    val dueDay: Int = 15,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
