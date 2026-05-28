package com.finix.app.data.repository

import com.finix.app.data.local.dao.CardDao
import com.finix.app.data.local.entity.CardEntity
import com.finix.app.domain.model.Card
import com.finix.app.domain.model.CardType
import com.finix.app.domain.repository.CardRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val cardDao: CardDao,
    private val firestore: FirebaseFirestore
) : CardRepository {

    override suspend fun addCard(card: Card): Result<Unit> = try {
        val entity = card.toEntity()
        cardDao.insertCard(entity)
        firestore.collection("cards").document(card.id).set(entity).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateCard(card: Card): Result<Unit> = try {
        val entity = card.toEntity()
        cardDao.updateCard(entity)
        firestore.collection("cards").document(card.id).set(entity).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteCard(cardId: String): Result<Unit> = try {
        cardDao.deleteCard(cardDao.getCardById(cardId) ?: throw Exception("Cartão não encontrado"))
        firestore.collection("cards").document(cardId).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getCard(cardId: String): Result<Card?> = try {
        val entity = cardDao.getCardById(cardId)
        Result.success(entity?.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getActiveCardsByUserId(userId: String): Flow<List<Card>> {
        return cardDao.getActiveCardsByUserIdFlow(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getCardsByUserId(userId: String): Result<List<Card>> = try {
        val entities = cardDao.getCardsByUserId(userId)
        Result.success(entities.map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun Card.toEntity() = CardEntity(
        id = id,
        userId = userId,
        name = name,
        cardNumber = cardNumber,
        cardHolder = cardHolder,
        expiryDate = expiryDate,
        type = type.value,
        limit = limit,
        color = color,
        issuer = issuer,
        closingDay = closingDay,
        dueDay = dueDay,
        isActive = isActive,
        updatedAt = System.currentTimeMillis()
    )

    private fun CardEntity.toDomain() = Card(
        id = id,
        userId = userId,
        name = name,
        cardNumber = cardNumber,
        cardHolder = cardHolder,
        expiryDate = expiryDate,
        type = CardType.values().first { it.value == type },
        limit = limit,
        color = color,
        issuer = issuer,
        closingDay = closingDay,
        dueDay = dueDay,
        isActive = isActive,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
