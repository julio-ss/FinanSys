package com.finix.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finix.app.domain.model.User
import com.finix.app.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.observeCurrentUser().collect { user ->
                _user.value = user
            }
        }
    }

    fun updateProfile(user: User) {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            userRepository.updateUser(user)
                .onSuccess {
                    _uiState.value = ProfileUiState.Success
                }
                .onFailure { error ->
                    _uiState.value = ProfileUiState.Error(error.message ?: "Erro ao atualizar perfil")
                }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            val userId = _user.value?.id ?: return@launch
            userRepository.deleteUser(userId)
                .onSuccess {
                    _uiState.value = ProfileUiState.AccountDeleted
                }
                .onFailure { error ->
                    _uiState.value = ProfileUiState.Error(error.message ?: "Erro ao deletar conta")
                }
        }
    }
}

sealed class ProfileUiState {
    data object Idle : ProfileUiState()
    data object Loading : ProfileUiState()
    data object Success : ProfileUiState()
    data object AccountDeleted : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}
