package com.finix.app.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finix.app.presentation.component.ExpenseChartCard
import com.finix.app.presentation.component.FinixCard
import com.finix.app.presentation.viewmodel.DashboardViewModel
import com.finix.app.presentation.viewmodel.DashboardUiState
import com.finix.app.presentation.theme.Expense
import com.finix.app.presentation.theme.Income

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavHostController,
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by dashboardViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard Completo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { dashboardViewModel.refreshData() }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Atualizar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        when (uiState) {
            is DashboardUiState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is DashboardUiState.Success -> {
                val successState = uiState as DashboardUiState.Success
                val dashboard = successState.dashboard

                DashboardContent(
                    dashboard = dashboard,
                    paddingValues = paddingValues
                )
            }
            is DashboardUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = (uiState as DashboardUiState.Error).message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardContent(
    dashboard: com.finix.app.domain.model.Dashboard,
    paddingValues: androidx.compose.foundation.layout.PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            FinixCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Saldo Total", style = MaterialTheme.typography.bodySmall)
                    Text(
                        text = "R$ ${String.format("%.2f", dashboard.totalBalance)}",
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            DashboardIncomeExpenseRow(dashboard)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            DashboardExpenseChart(dashboard)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Últimas Transações", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(dashboard.recentTransactions.size) { index ->
            val transaction = dashboard.recentTransactions[index]
            FinixCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        transaction.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${if (transaction.type.value == "expense") "-" else "+"}R$ ${String.format("%.2f", transaction.amount)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (transaction.type.value == "expense") Expense else Income
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun DashboardExpenseChart(dashboard: com.finix.app.domain.model.Dashboard) {
    ExpenseChartCard(
        totalIncome = dashboard.totalIncome,
        totalExpense = dashboard.totalExpense
    )
}

@Composable
private fun DashboardIncomeExpenseRow(dashboard: com.finix.app.domain.model.Dashboard) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FinixCard(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Receita",
                    style = MaterialTheme.typography.labelSmall,
                    color = Income
                )
                Text(
                    text = "R$ ${String.format("%.2f", dashboard.totalIncome)}",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        FinixCard(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Despesas",
                    style = MaterialTheme.typography.labelSmall,
                    color = Expense
                )
                Text(
                    text = "R$ ${String.format("%.2f", dashboard.totalExpense)}",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}
