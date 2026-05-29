package com.finix.app.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finix.app.presentation.component.FinixCard
import com.finix.app.presentation.component.FinixStatCard
import com.finix.app.presentation.navigation.Route
import com.finix.app.presentation.viewmodel.DashboardViewModel
import com.finix.app.presentation.viewmodel.DashboardUiState
import com.finix.app.presentation.theme.Expense
import com.finix.app.presentation.theme.Income
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.CircularProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    var selectedNavItem by remember { mutableIntStateOf(0) }
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finix") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                actions = {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Mais opções")
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Escanear Comprovante") },
                            leadingIcon = { Icon(Icons.Filled.CameraAlt, contentDescription = null) },
                            onClick = {
                                menuExpanded = false
                                navController.navigate(Route.ReceiptScanner.route)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Exportar Relatório") },
                            leadingIcon = { Icon(Icons.Filled.FileDownload, contentDescription = null) },
                            onClick = {
                                menuExpanded = false
                                navController.navigate(Route.Export.route)
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (selectedNavItem == 0) {
                ExtendedFloatingActionButton(
                    onClick = { navController.navigate(Route.ReceiptScanner.route) },
                    icon = { Icon(Icons.Filled.CameraAlt, contentDescription = null) },
                    text = { Text("Comprovante") }
                )
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedNavItem == 0,
                    onClick = { selectedNavItem = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Receipt, contentDescription = "Transações") },
                    label = { Text("Transações") },
                    selected = selectedNavItem == 1,
                    onClick = { selectedNavItem = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.CreditCard, contentDescription = "Cartões") },
                    label = { Text("Cartões") },
                    selected = selectedNavItem == 2,
                    onClick = { selectedNavItem = 2 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Assessment, contentDescription = "Relatórios") },
                    label = { Text("Relatórios") },
                    selected = selectedNavItem == 3,
                    onClick = { selectedNavItem = 3 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountBalance, contentDescription = "Finanças") },
                    label = { Text("Finanças") },
                    selected = selectedNavItem == 4,
                    onClick = {
                        selectedNavItem = 4
                        navController.navigate(Route.FinancialHealth.route)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AutoAwesome, contentDescription = "IA") },
                    label = { Text("IA") },
                    selected = selectedNavItem == 5,
                    onClick = {
                        selectedNavItem = 5
                        navController.navigate(Route.AIInsights.route)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.EmojiEvents, contentDescription = "Conquistas") },
                    label = { Text("Conquistas") },
                    selected = selectedNavItem == 6,
                    onClick = {
                        selectedNavItem = 6
                        navController.navigate(Route.Achievements.route)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") },
                    selected = selectedNavItem == 7,
                    onClick = {
                        selectedNavItem = 7
                        navController.navigate(Route.Profile.route)
                    }
                )
            }
        }
    ) { paddingValues ->
        when (selectedNavItem) {
            0 -> DashboardContent(
                modifier = Modifier.padding(paddingValues),
                dashboardViewModel = dashboardViewModel
            )
            1 -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Tela de Transações")
            }
            2 -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Tela de Cartões")
            }
            3 -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Tela de Relatórios")
            }
        }
    }
}

@Composable
fun DashboardContent(
    modifier: Modifier = Modifier,
    dashboardViewModel: DashboardViewModel
) {
    val uiState by dashboardViewModel.uiState.collectAsState()

    when (uiState) {
        is DashboardUiState.Loading -> {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is DashboardUiState.Success -> {
            val dashboard = (uiState as DashboardUiState.Success).dashboard
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Dashboard",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                FinixCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Saldo Total", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = "R$ ${String.format("%.2f", dashboard.totalBalance)}",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                FinixStatCard(
                    title = "Receita",
                    value = "R$ ${String.format("%.2f", dashboard.monthlyIncome)}",
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Income.copy(alpha = 0.1f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                FinixStatCard(
                    title = "Despesas",
                    value = "R$ ${String.format("%.2f", dashboard.monthlyExpense)}",
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Expense.copy(alpha = 0.1f)
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        is DashboardUiState.Error -> {
            Column(
                modifier = modifier.fillMaxSize(),
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
