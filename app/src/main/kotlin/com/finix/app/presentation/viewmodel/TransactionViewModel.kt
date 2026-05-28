package com.finix.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.finix.app.domain.model.Transaction
import com.finix.app.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    private val _pagedTransactions = MutableStateFlow<PagingData<Transaction>>(PagingData.empty())
    val pagedTransactions: StateFlow<PagingData<Transaction>> = _pagedTransactions.asStateFlow()

    private val _uiState = MutableStateFlow<TransactionUiState>(TransactionUiState.Idle)
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    fun loadTransactions(userId: String) {
        viewModelScope.launch {
            transactionRepository.getTransactionsByUserId(userId).collect { transactions ->
                _transactions.value = transactions
            }
        }
    }

    fun loadPagedTransactions(userId: String) {
        viewModelScope.launch {
            transactionRepository.getTransactionsPaged(userId).collect { pagingData ->
                _pagedTransactions.value = pagingData
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            _uiState.value = TransactionUiState.Loading
            transactionRepository.addTransaction(transaction)
                .onSuccess {
                    _uiState.value = TransactionUiState.Success
                }
                .onFailure { error ->
                    _uiState.value = TransactionUiState.Error(error.message ?: "Erro ao adicionar transação")
                }
        }
    }

    fun deleteTransaction(transactionId: String) {
        viewModelScope.launch {
            transactionRepository.deleteTransaction(transactionId)
                .onSuccess {
                    _uiState.value = TransactionUiState.Success
                }
                .onFailure { error ->
                    _uiState.value = TransactionUiState.Error(error.message ?: "Erro ao deletar transação")
                }
        }
    }
}

sealed class TransactionUiState {
    data object Idle : TransactionUiState()
    data object Loading : TransactionUiState()
    data object Success : TransactionUiState()
    data class Error(val message: String) : TransactionUiState()
}
