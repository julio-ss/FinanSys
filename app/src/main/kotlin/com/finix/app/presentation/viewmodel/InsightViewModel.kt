package com.finix.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finix.app.domain.model.Insight
import com.finix.app.domain.repository.InsightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsightViewModel @Inject constructor(
    private val insightRepository: InsightRepository
) : ViewModel() {

    private val _insights = MutableStateFlow<List<Insight>>(emptyList())
    val insights: StateFlow<List<Insight>> = _insights.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    fun loadInsights(userId: String) {
        viewModelScope.launch {
            insightRepository.getActiveInsightsByUserId(userId).collect { insights ->
                _insights.value = insights
            }
        }
    }

    fun generateAIInsights(userId: String) {
        viewModelScope.launch {
            _isGenerating.value = true
            insightRepository.generateAIInsights(userId)
                .onSuccess {
                    loadInsights(userId)
                }
                .onFailure {
                    _isGenerating.value = false
                }
        }
    }
}
