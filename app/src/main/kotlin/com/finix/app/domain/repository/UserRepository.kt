package com.finix.app.domain.repository

import com.finix.app.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(user: User): Result<Unit>
    suspend fun loginUser(email: String, password: String): Result<User>
    suspend fun loginWithGoogle(idToken: String): Result<User>
    suspend fun getCurrentUser(): Result<User?>
    suspend fun updateUser(user: User): Result<Unit>
    suspend fun deleteUser(userId: String): Result<Unit>
    suspend fun logout(): Result<Unit>
    fun observeCurrentUser(): Flow<User?>
    suspend fun resetPassword(email: String): Result<Unit>
}
