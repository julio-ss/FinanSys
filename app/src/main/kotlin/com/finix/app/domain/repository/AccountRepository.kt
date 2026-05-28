package com.finix.app.domain.repository

import com.finix.app.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun addAccount(account: Account): Result<Unit>
    suspend fun updateAccount(account: Account): Result<Unit>
    suspend fun deleteAccount(accountId: String): Result<Unit>
    suspend fun getAccount(accountId: String): Result<Account?>
    fun getActiveAccountsByUserId(userId: String): Flow<List<Account>>
    suspend fun getAccountsByUserId(userId: String): Result<List<Account>>
    suspend fun getTotalBalance(userId: String): Result<Double>
}
