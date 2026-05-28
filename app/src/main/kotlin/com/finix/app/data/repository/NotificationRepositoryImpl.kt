package com.finix.app.data.repository

import com.finix.app.data.local.dao.NotificationDao
import com.finix.app.data.local.entity.NotificationEntity
import com.finix.app.domain.model.Notification
import com.finix.app.domain.model.NotificationType
import com.finix.app.domain.repository.NotificationRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDao: NotificationDao,
    private val firestore: FirebaseFirestore
) : NotificationRepository {

    override suspend fun addNotification(notification: Notification): Result<Unit> = try {
        notificationDao.insertNotification(notification.toEntity())
        firestore.collection("notifications").document(notification.id).set(notification.toEntity()).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun markAsRead(userId: String, notificationId: String): Result<Unit> = try {
        notificationDao.markAsRead(userId, notificationId)
        firestore.collection("notifications").document(notificationId).update("isRead", true).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteNotification(notificationId: String): Result<Unit> = try {
        notificationDao.deleteNotification(notificationDao.getNotificationById(notificationId) ?: throw Exception())
        firestore.collection("notifications").document(notificationId).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getNotificationsByUserId(userId: String): Flow<List<Notification>> =
        notificationDao.getNotificationsByUserIdFlow(userId).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun getUnreadNotifications(userId: String): Result<List<Notification>> = try {
        Result.success(notificationDao.getUnreadNotifications(userId).map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun Notification.toEntity() = NotificationEntity(
        id, userId, title, message, type.value, relatedId, isRead, createdAt
    )

    private fun NotificationEntity.toDomain() = Notification(
        id, userId, title, message, NotificationType.values().first { it.value == type }, relatedId, isRead, createdAt
    )
}
