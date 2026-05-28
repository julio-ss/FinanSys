package com.finix.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(
    tableName = "categories",
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
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val name: String,
    val icon: String,
    val color: String,
    val type: String, // "income" or "expense"
    val isCustom: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
