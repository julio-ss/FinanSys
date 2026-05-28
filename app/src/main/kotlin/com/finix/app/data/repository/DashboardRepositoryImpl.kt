package com.finix.app.data.repository

import com.finix.app.domain.model.Dashboard
import com.finix.app.domain.repository.AccountRepository
import com.finix.app.domain.repository.CardRepository
import com.finix.app.domain.repository.DashboardRepository
import com.finix.app.domain.repository.GoalRepository
import com.finix.app.domain.repository.InsightRepository
import com.finix.app.domain.repository.SubscriptionRepository
import com.finix.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.Calendar
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val cardRepository: CardRepository,
    private val accountRepository: AccountRepository,
    private val goalRepository: GoalRepository,
    private val subscriptionRepository: SubscriptionRepository,
    private val insightRepository: InsightRepository
) : DashboardRepository {

    override fun getDashboard(userId: String): Flow<Dashboard> {
        val calendar = Calendar.getInstance()
        val startOfMonth = calendar.apply { set(Calendar.DAY_OF_MONTH, 1) }.timeInMillis
        val endOfMonth = calendar.apply { set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) }.timeInMillis

        return combine(
            transactionRepository.getTransactionsByUserId(userId),
            cardRepository.getActiveCardsByUserId(userId),
            accountRepository.getActiveAccountsByUserId(userId),
            goalRepository.getActiveGoalsByUserId(userId),
            subscriptionRepository.getActiveSubscriptionsByUserId(userId),
            insightRepository.getActiveInsightsByUserId(userId)
        ) { transactions, cards, accounts, goals, subscriptions, insights ->
            val monthlyIncome = transactions.filter { it.type.value == "income" && it.date in startOfMonth..endOfMonth }
                .sumOf { it.amount }
            val monthlyExpense = transactions.filter { it.type.value == "expense" && it.date in startOfMonth..endOfMonth }
                .sumOf { it.amount }
            val totalBalance = accounts.sumOf { it.balance }

            Dashboard(
                totalBalance = totalBalance,
                monthlyIncome = monthlyIncome,
                monthlyExpense = monthlyExpense,
                recentTransactions = transactions.take(5),
                activeGoals = goals,
                activeCards = cards,
                activeSubscriptions = subscriptions,
                insights = insights
            )
        }
    }

    override suspend fun refreshDashboard(userId: String): Result<Dashboard> = try {
        Result.success(Dashboard())
    } catch (e: Exception) {
        Result.failure(e)
    }
}
