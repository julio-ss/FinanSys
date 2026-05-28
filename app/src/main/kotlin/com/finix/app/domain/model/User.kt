package com.finix.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    val photoUrl: String? = null,
    val currency: String = "BRL",
    val language: String = "pt",
    val theme: String = "system",
    val biometricEnabled: Boolean = false,
    val pinEnabled: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
