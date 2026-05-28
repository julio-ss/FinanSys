package com.finix.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.finix.app.data.local.entity.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(cards: List<CardEntity>)

    @Update
    suspend fun updateCard(card: CardEntity)

    @Delete
    suspend fun deleteCard(card: CardEntity)

    @Query("SELECT * FROM cards WHERE id = :cardId")
    suspend fun getCardById(cardId: String): CardEntity?

    @Query("SELECT * FROM cards WHERE userId = :userId AND isActive = 1 ORDER BY name")
    fun getActiveCardsByUserIdFlow(userId: String): Flow<List<CardEntity>>

    @Query("SELECT * FROM cards WHERE userId = :userId ORDER BY name")
    suspend fun getCardsByUserId(userId: String): List<CardEntity>

    @Query("DELETE FROM cards WHERE userId = :userId")
    suspend fun deleteUserCards(userId: String)
}
