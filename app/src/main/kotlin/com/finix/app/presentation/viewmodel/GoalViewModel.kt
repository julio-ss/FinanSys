package com.finix.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finix.app.domain.model.Goal
import com.finix.app.domain.repository.GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : ViewModel() {

    private val _goals = MutableStateFlow<List<Goal>>(emptyList())
    val goals: StateFlow<List<Goal>> = _goals.asStateFlow()

    private val _uiState = MutableStateFlow<GoalUiState>(GoalUiState.Idle)
    val uiState: StateFlow<GoalUiState> = _uiState.asStateFlow()

    fun loadGoals(userId: String) {
        viewModelScope.launch {
            goalRepository.getActiveGoalsByUserId(userId).collect { goals ->
                _goals.value = goals
            }
        }
    }

    fun addGoal(goal: Goal) {
        viewModelScope.launch {
            _uiState.value = GoalUiState.Loading
            goalRepository.addGoal(goal)
                .onSuccess { _uiState.value = GoalUiState.Success }
                .onFailure { error ->
                    _uiState.value = GoalUiState.Error(error.message ?: "Erro ao criar meta")
                }
        }
    }

    fun updateGoal(goal: Goal) {
        viewModelScope.launch {
            goalRepository.updateGoal(goal)
                .onSuccess { _uiState.value = GoalUiState.Success }
                .onFailure { error ->
                    _uiState.value = GoalUiState.Error(error.message ?: "Erro ao atualizar meta")
                }
        }
    }

    fun deleteGoal(goalId: String) {
        viewModelScope.launch {
            goalRepository.deleteGoal(goalId)
                .onSuccess { _uiState.value = GoalUiState.Success }
                .onFailure { error ->
                    _uiState.value = GoalUiState.Error(error.message ?: "Erro ao deletar meta")
                }
        }
    }
}

sealed class GoalUiState {
    data object Idle : GoalUiState()
    data object Loading : GoalUiState()
    data object Success : GoalUiState()
    data class Error(val message: String) : GoalUiState()
}
