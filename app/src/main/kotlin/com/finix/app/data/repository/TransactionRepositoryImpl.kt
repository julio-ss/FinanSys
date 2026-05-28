package com.finix.app.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.finix.app.data.local.dao.TransactionDao
import com.finix.app.data.local.entity.TransactionEntity
import com.finix.app.domain.model.Transaction
import com.finix.app.domain.model.TransactionType
import com.finix.app.domain.repository.TransactionRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val firestore: FirebaseFirestore
) : TransactionRepository {

    override suspend fun addTransaction(transaction: Transaction): Result<Unit> = try {
        val entity = transaction.toEntity()
        transactionDao.insertTransaction(entity)
        firestore.collection("transactions").document(transaction.id).set(entity).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateTransaction(transaction: Transaction): Result<Unit> = try {
        val entity = transaction.toEntity()
        transactionDao.updateTransaction(entity)
        firestore.collection("transactions").document(transaction.id).set(entity).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteTransaction(transactionId: String): Result<Unit> = try {
        transactionDao.deleteTransaction(transactionDao.getTransactionById(transactionId) ?: throw Exception("Transação não encontrada"))
        firestore.collection("transactions").document(transactionId).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getTransaction(transactionId: String): Result<Transaction?> = try {
        val entity = transactionDao.getTransactionById(transactionId)
        Result.success(entity?.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getTransactionsByUserId(userId: String): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByUserIdFlow(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTransactionsPaged(userId: String): Flow<PagingData<Transaction>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { transactionDao.getTransactionsPaged(userId) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun getTransactionsByDateRange(userId: String, startDate: Long, endDate: Long): Result<List<Transaction>> = try {
        val entities = transactionDao.getTransactionsByDateRange(userId, startDate, endDate)
        Result.success(entities.map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getTransactionsByCategory(userId: String, categoryId: String): Result<List<Transaction>> = try {
        val entities = transactionDao.getTransactionsByCategory(userId, categoryId)
        Result.success(entities.map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getTotalIncome(userId: String, startDate: Long, endDate: Long): Result<Double> = try {
        val total = transactionDao.getTotalIncome(userId, startDate, endDate) ?: 0.0
        Result.success(total)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getTotalExpense(userId: String, startDate: Long, endDate: Long): Result<Double> = try {
        val total = transactionDao.getTotalExpense(userId, startDate, endDate) ?: 0.0
        Result.success(total)
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun Transaction.toEntity() = TransactionEntity(
        id = id,
        userId = userId,
        categoryId = categoryId,
        accountId = accountId,
        amount = amount,
        description = description,
        type = type.value,
        date = date,
        tags = tags.joinToString(","),
        receiptUrl = receiptUrl,
        isRecurring = isRecurring,
        recurrenceType = recurrenceType,
        syncedWithCloud = syncedWithCloud,
        updatedAt = System.currentTimeMillis()
    )

    private fun TransactionEntity.toDomain() = Transaction(
        id = id,
        userId = userId,
        categoryId = categoryId,
        accountId = accountId,
        amount = amount,
        description = description,
        type = TransactionType.values().first { it.value == type },
        date = date,
        tags = tags.split(",").filter { it.isNotEmpty() },
        receiptUrl = receiptUrl,
        isRecurring = isRecurring,
        recurrenceType = recurrenceType,
        syncedWithCloud = syncedWithCloud,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
