package com.finix.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Dashboard(
    val totalBalance: Double = 0.0,
    val monthlyIncome: Double = 0.0,
    val monthlyExpense: Double = 0.0,
    val recentTransactions: List<Transaction> = emptyList(),
    val activeGoals: List<Goal> = emptyList(),
    val activeCards: List<Card> = emptyList(),
    val activeSubscriptions: List<Subscription> = emptyList(),
    val insights: List<Insight> = emptyList(),
    val monthlyComparison: MonthlyComparison? = null
)

@Serializable
data class MonthlyComparison(
    val currentMonth: MonthData,
    val previousMonth: MonthData,
    val trend: Double = 0.0 // percentual de mudança
)

@Serializable
data class MonthData(
    val income: Double = 0.0,
    val expense: Double = 0.0,
    val balance: Double = 0.0
)
