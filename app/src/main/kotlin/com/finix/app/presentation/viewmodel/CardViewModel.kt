package com.finix.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finix.app.domain.model.Card
import com.finix.app.domain.repository.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val cardRepository: CardRepository
) : ViewModel() {

    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> = _cards.asStateFlow()

    private val _uiState = MutableStateFlow<CardUiState>(CardUiState.Idle)
    val uiState: StateFlow<CardUiState> = _uiState.asStateFlow()

    fun loadCards(userId: String) {
        viewModelScope.launch {
            cardRepository.getActiveCardsByUserId(userId).collect { cards ->
                _cards.value = cards
            }
        }
    }

    fun addCard(card: Card) {
        viewModelScope.launch {
            _uiState.value = CardUiState.Loading
            cardRepository.addCard(card)
                .onSuccess { _uiState.value = CardUiState.Success }
                .onFailure { error ->
                    _uiState.value = CardUiState.Error(error.message ?: "Erro ao adicionar cartão")
                }
        }
    }

    fun deleteCard(cardId: String) {
        viewModelScope.launch {
            cardRepository.deleteCard(cardId)
                .onSuccess { _uiState.value = CardUiState.Success }
                .onFailure { error ->
                    _uiState.value = CardUiState.Error(error.message ?: "Erro ao deletar cartão")
                }
        }
    }
}

sealed class CardUiState {
    data object Idle : CardUiState()
    data object Loading : CardUiState()
    data object Success : CardUiState()
    data class Error(val message: String) : CardUiState()
}
