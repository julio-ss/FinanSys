package com.finix.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val id: String,
    val userId: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val relatedId: String? = null,
    val isRead: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

enum class NotificationType(val value: String) {
    TRANSACTION("transaction"),
    GOAL("goal"),
    CARD("card"),
    SUBSCRIPTION("subscription"),
    ALERT("alert")
}
