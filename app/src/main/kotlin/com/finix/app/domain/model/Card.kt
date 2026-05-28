package com.finix.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val id: String,
    val userId: String,
    val name: String,
    val cardNumber: String,
    val cardHolder: String,
    val expiryDate: String,
    val type: CardType,
    val limit: Double? = null,
    val color: String,
    val issuer: String,
    val closingDay: Int = 10,
    val dueDay: Int = 15,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class CardType(val value: String) {
    CREDIT("credit"),
    DEBIT("debit")
}
