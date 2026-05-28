package com.finix.app.domain.repository

import com.finix.app.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun addCategory(category: Category): Result<Unit>
    suspend fun updateCategory(category: Category): Result<Unit>
    suspend fun deleteCategory(categoryId: String): Result<Unit>
    suspend fun getCategory(categoryId: String): Result<Category?>
    fun getCategoriesByUserId(userId: String): Flow<List<Category>>
    suspend fun getCategoriesByType(userId: String, type: String): Result<List<Category>>
    suspend fun initializeDefaultCategories(userId: String): Result<Unit>
}
