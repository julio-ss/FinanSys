package com.finix.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val id: String,
    val userId: String,
    val categoryId: String,
    val accountId: String,
    val amount: Double,
    val description: String,
    val type: TransactionType,
    val date: Long,
    val tags: List<String> = emptyList(),
    val receiptUrl: String? = null,
    val isRecurring: Boolean = false,
    val recurrenceType: String? = null,
    val syncedWithCloud: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class TransactionType(val value: String) {
    INCOME("income"),
    EXPENSE("expense"),
    TRANSFER("transfer")
}
