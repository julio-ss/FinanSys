package com.finix.app.domain.repository

import com.finix.app.domain.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun addNotification(notification: Notification): Result<Unit>
    suspend fun markAsRead(userId: String, notificationId: String): Result<Unit>
    suspend fun deleteNotification(notificationId: String): Result<Unit>
    fun getNotificationsByUserId(userId: String): Flow<List<Notification>>
    suspend fun getUnreadNotifications(userId: String): Result<List<Notification>>
}
