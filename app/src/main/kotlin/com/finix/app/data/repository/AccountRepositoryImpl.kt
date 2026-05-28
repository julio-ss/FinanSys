package com.finix.app.data.repository

import com.finix.app.data.local.dao.AccountDao
import com.finix.app.data.local.entity.AccountEntity
import com.finix.app.domain.model.Account
import com.finix.app.domain.repository.AccountRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val firestore: FirebaseFirestore
) : AccountRepository {

    override suspend fun addAccount(account: Account): Result<Unit> = try {
        accountDao.insertAccount(account.toEntity())
        firestore.collection("accounts").document(account.id).set(account.toEntity()).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateAccount(account: Account): Result<Unit> = try {
        accountDao.updateAccount(account.toEntity())
        firestore.collection("accounts").document(account.id).set(account.toEntity()).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteAccount(accountId: String): Result<Unit> = try {
        accountDao.deleteAccount(accountDao.getAccountById(accountId) ?: throw Exception())
        firestore.collection("accounts").document(accountId).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAccount(accountId: String): Result<Account?> = try {
        Result.success(accountDao.getAccountById(accountId)?.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getActiveAccountsByUserId(userId: String): Flow<List<Account>> =
        accountDao.getActiveAccountsByUserIdFlow(userId).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun getAccountsByUserId(userId: String): Result<List<Account>> = try {
        Result.success(accountDao.getAccountsByUserId(userId).map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getTotalBalance(userId: String): Result<Double> = try {
        Result.success(accountDao.getTotalBalance(userId) ?: 0.0)
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun Account.toEntity() = AccountEntity(
        id, userId, name, type, bank, accountNumber, balance, currency, color, icon, isActive, updatedAt = System.currentTimeMillis()
    )

    private fun AccountEntity.toDomain() = Account(
        id, userId, name, type, bank, accountNumber, balance, currency, color, icon, isActive, createdAt, updatedAt
    )
}
