package com.finix.app.domain.repository

import androidx.paging.PagingData
import com.finix.app.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun addTransaction(transaction: Transaction): Result<Unit>
    suspend fun updateTransaction(transaction: Transaction): Result<Unit>
    suspend fun deleteTransaction(transactionId: String): Result<Unit>
    suspend fun getTransaction(transactionId: String): Result<Transaction?>
    fun getTransactionsByUserId(userId: String): Flow<List<Transaction>>
    fun getTransactionsPaged(userId: String): Flow<PagingData<Transaction>>
    suspend fun getTransactionsByDateRange(userId: String, startDate: Long, endDate: Long): Result<List<Transaction>>
    suspend fun getTransactionsByCategory(userId: String, categoryId: String): Result<List<Transaction>>
    suspend fun getTotalIncome(userId: String, startDate: Long, endDate: Long): Result<Double>
    suspend fun getTotalExpense(userId: String, startDate: Long, endDate: Long): Result<Double>
}
