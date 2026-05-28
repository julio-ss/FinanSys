package com.finix.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finix.app.domain.model.Dashboard
import com.finix.app.domain.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun loadDashboard(userId: String) {
        viewModelScope.launch {
            dashboardRepository.getDashboard(userId).collect { dashboard ->
                _uiState.value = DashboardUiState.Success(dashboard)
            }
        }
    }

    fun refresh(userId: String) {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading
            dashboardRepository.refreshDashboard(userId).onSuccess { dashboard ->
                _uiState.value = DashboardUiState.Success(dashboard)
            }.onFailure { error ->
                _uiState.value = DashboardUiState.Error(error.message ?: "Erro desconhecido")
            }
        }
    }
}

sealed class DashboardUiState {
    data object Loading : DashboardUiState()
    data class Success(val dashboard: Dashboard) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}
