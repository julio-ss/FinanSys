package com.finix.app.data.repository

import com.finix.app.data.local.dao.CategoryDao
import com.finix.app.data.local.entity.CategoryEntity
import com.finix.app.domain.model.Category
import com.finix.app.domain.model.DefaultCategories
import com.finix.app.domain.repository.CategoryRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val firestore: FirebaseFirestore
) : CategoryRepository {

    override suspend fun addCategory(category: Category): Result<Unit> = try {
        val entity = category.toEntity()
        categoryDao.insertCategory(entity)
        firestore.collection("categories").document(category.id).set(entity).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateCategory(category: Category): Result<Unit> = try {
        val entity = category.toEntity()
        categoryDao.updateCategory(entity)
        firestore.collection("categories").document(category.id).set(entity).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteCategory(categoryId: String): Result<Unit> = try {
        categoryDao.deleteCategory(categoryDao.getCategoryById(categoryId) ?: throw Exception("Categoria não encontrada"))
        firestore.collection("categories").document(categoryId).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getCategory(categoryId: String): Result<Category?> = try {
        val entity = categoryDao.getCategoryById(categoryId)
        Result.success(entity?.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getCategoriesByUserId(userId: String): Flow<List<Category>> {
        return categoryDao.getCategoriesByUserIdFlow(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getCategoriesByType(userId: String, type: String): Result<List<Category>> = try {
        val entities = categoryDao.getCategoriesByType(userId, type)
        Result.success(entities.map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun initializeDefaultCategories(userId: String): Result<Unit> = try {
        val allCategories = DefaultCategories.getExpenseCategories() + DefaultCategories.getIncomeCategories()
        val entities = allCategories.map { it.copy(userId = userId).toEntity() }
        categoryDao.insertCategories(entities)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun Category.toEntity() = CategoryEntity(
        id = id,
        userId = userId,
        name = name,
        icon = icon,
        color = color,
        type = type,
        isCustom = isCustom,
        createdAt = createdAt
    )

    private fun CategoryEntity.toDomain() = Category(
        id = id,
        userId = userId,
        name = name,
        icon = icon,
        color = color,
        type = type,
        isCustom = isCustom,
        createdAt = createdAt
    )
}
