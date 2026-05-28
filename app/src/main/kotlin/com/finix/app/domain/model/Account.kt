package com.finix.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val id: String,
    val userId: String,
    val name: String,
    val type: String,
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
