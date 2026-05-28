package com.finix.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(
    tableName = "accounts",
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
data class AccountEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val name: String,
    val type: String, // "checking", "savings", "investment", etc.
    val bank: String,
    val accountNumber: String? = null,
    val balance: Double = 0.0,
    val currency: String = "BRL",
    val color: String,
    val icon: String,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
