package com.finix.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.finix.app.data.local.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountEntity)

    @Update
    suspend fun updateAccount(account: AccountEntity)

    @Delete
    suspend fun deleteAccount(account: AccountEntity)

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    suspend fun getAccountById(accountId: String): AccountEntity?

    @Query("SELECT * FROM accounts WHERE userId = :userId AND isActive = 1 ORDER BY name")
    fun getActiveAccountsByUserIdFlow(userId: String): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE userId = :userId ORDER BY name")
    suspend fun getAccountsByUserId(userId: String): List<AccountEntity>

    @Query("DELETE FROM accounts WHERE userId = :userId")
    suspend fun deleteUserAccounts(userId: String)

    @Query("SELECT SUM(balance) FROM accounts WHERE userId = :userId AND isActive = 1")
    suspend fun getTotalBalance(userId: String): Double?
}
