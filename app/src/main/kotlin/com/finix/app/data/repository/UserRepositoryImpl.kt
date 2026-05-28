package com.finix.app.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.finix.app.data.local.dao.UserDao
import com.finix.app.data.local.entity.UserEntity
import com.finix.app.domain.model.User
import com.finix.app.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "finix_preferences")

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val context: Context
) : UserRepository {

    private val dataStore = context.dataStore
    private val currentUserIdKey = stringPreferencesKey("current_user_id")

    override suspend fun registerUser(user: User): Result<Unit> = try {
        val firebaseUser = firebaseAuth.createUserWithEmailAndPassword(
            user.email,
            "temp_password" // será alterado pelo usuário
        ).await().user ?: throw Exception("Falha ao criar usuário")

        val userEntity = UserEntity(
            id = firebaseUser.uid,
            name = user.name,
            email = user.email,
            currency = user.currency,
            language = user.language
        )

        userDao.insertUser(userEntity)
        firestore.collection("users").document(firebaseUser.uid).set(userEntity).await()

        dataStore.edit { preferences ->
            preferences[currentUserIdKey] = firebaseUser.uid
        }

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun loginUser(email: String, password: String): Result<User> = try {
        val firebaseUser = firebaseAuth.signInWithEmailAndPassword(email, password).await().user
            ?: throw Exception("Falha ao fazer login")

        val userEntity = userDao.getUserById(firebaseUser.uid)
            ?: throw Exception("Usuário não encontrado no banco local")

        dataStore.edit { preferences ->
            preferences[currentUserIdKey] = firebaseUser.uid
        }

        Result.success(userEntity.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun loginWithGoogle(idToken: String): Result<User> = try {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val firebaseUser = firebaseAuth.signInWithCredential(credential).await().user
            ?: throw Exception("Falha ao fazer login com Google")

        var userEntity = userDao.getUserById(firebaseUser.uid)
        if (userEntity == null) {
            userEntity = UserEntity(
                id = firebaseUser.uid,
                name = firebaseUser.displayName ?: "Usuário",
                email = firebaseUser.email ?: "",
                photoUrl = firebaseUser.photoUrl?.toString()
            )
            userDao.insertUser(userEntity)
            firestore.collection("users").document(firebaseUser.uid).set(userEntity).await()
        }

        dataStore.edit { preferences ->
            preferences[currentUserIdKey] = firebaseUser.uid
        }

        Result.success(userEntity.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getCurrentUser(): Result<User?> = try {
        val currentFirebaseUser = firebaseAuth.currentUser
        val userId = currentFirebaseUser?.uid ?: return Result.success(null)

        val userEntity = userDao.getUserById(userId)
        Result.success(userEntity?.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateUser(user: User): Result<Unit> = try {
        val userEntity = UserEntity(
            id = user.id,
            name = user.name,
            email = user.email,
            photoUrl = user.photoUrl,
            currency = user.currency,
            language = user.language,
            theme = user.theme,
            biometricEnabled = user.biometricEnabled,
            pinEnabled = user.pinEnabled,
            updatedAt = System.currentTimeMillis()
        )

        userDao.updateUser(userEntity)
        firestore.collection("users").document(user.id).set(userEntity).await()

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteUser(userId: String): Result<Unit> = try {
        firebaseAuth.currentUser?.delete()?.await()
        userDao.deleteUserById(userId)
        firestore.collection("users").document(userId).delete().await()

        dataStore.edit { preferences ->
            preferences.remove(currentUserIdKey)
        }

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun logout(): Result<Unit> = try {
        firebaseAuth.signOut()
        dataStore.edit { preferences ->
            preferences.remove(currentUserIdKey)
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun observeCurrentUser(): Flow<User?> {
        return dataStore.data.map { preferences ->
            val userId = preferences[currentUserIdKey]
            userId?.let { userDao.getUserById(it)?.toDomain() }
        }
    }

    override suspend fun resetPassword(email: String): Result<Unit> = try {
        firebaseAuth.sendPasswordResetEmail(email).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun UserEntity.toDomain() = User(
        id = id,
        name = name,
        email = email,
        photoUrl = photoUrl,
        currency = currency,
        language = language,
        theme = theme,
        biometricEnabled = biometricEnabled,
        pinEnabled = pinEnabled,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
