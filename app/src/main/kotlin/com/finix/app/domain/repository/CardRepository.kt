package com.finix.app.domain.repository

import com.finix.app.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    suspend fun addCard(card: Card): Result<Unit>
    suspend fun updateCard(card: Card): Result<Unit>
    suspend fun deleteCard(cardId: String): Result<Unit>
    suspend fun getCard(cardId: String): Result<Card?>
    fun getActiveCardsByUserId(userId: String): Flow<List<Card>>
    suspend fun getCardsByUserId(userId: String): Result<List<Card>>
}
