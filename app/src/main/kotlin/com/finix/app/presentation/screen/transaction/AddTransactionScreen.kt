package com.finix.app.presentation.screen.transaction

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
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finix.app.domain.model.Category
import com.finix.app.domain.model.Transaction
import com.finix.app.domain.model.TransactionType
import com.finix.app.presentation.component.FinixTextField
import com.finix.app.presentation.viewmodel.TransactionViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    navController: NavHostController,
    transactionViewModel: TransactionViewModel = hiltViewModel()
) {
    var amount by remember { mutableStateOf("") }
    var amountError by remember { mutableStateOf("") }

    var description by remember { mutableStateOf("") }
    var descriptionError by remember { mutableStateOf("") }

    var selectedType by remember { mutableStateOf(TransactionType.EXPENSE) }
    var showTypeMenu by remember { mutableStateOf(false) }

    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var showCategoryMenu by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }
    var dateText by remember {
        mutableStateOf(
            SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(selectedDate)
        )
    }

    var isLoading by remember { mutableStateOf(false) }
    var submitError by remember { mutableStateOf("") }

    val categories by transactionViewModel.categories.collectAsState()

    LaunchedEffect(Unit) {
        transactionViewModel.loadCategories("user_123") // Default user
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Transação") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Transaction Type Selection
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Tipo de Transação", style = MaterialTheme.typography.labelMedium)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { selectedType = TransactionType.INCOME },
                            modifier = Modifier.weight(1f),
                        ) {
                            Text("Receita")
                        }
                        OutlinedButton(
                            onClick = { selectedType = TransactionType.EXPENSE },
                            modifier = Modifier.weight(1f),
                        ) {
                            Text("Despesa")
                        }
                        OutlinedButton(
                            onClick = { selectedType = TransactionType.TRANSFER },
                            modifier = Modifier.weight(1f),
                        ) {
                            Text("Transferência")
                        }
                    }
                }
            }

            // Amount Input
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Valor (R$)", style = MaterialTheme.typography.labelMedium)
                    FinixTextField(
                        value = amount,
                        onValueChange = {
                            amount = it
                            amountError = ""
                        },
                        label = "Valor",
                        placeholder = "0,00",
                        isError = amountError.isNotEmpty(),
                        errorMessage = amountError,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Description Input
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Descrição", style = MaterialTheme.typography.labelMedium)
                    FinixTextField(
                        value = description,
                        onValueChange = {
                            description = it
                            descriptionError = ""
                        },
                        label = "Descrição",
                        placeholder = "Ex: Compra no supermercado",
                        isError = descriptionError.isNotEmpty(),
                        errorMessage = descriptionError,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Category Selection
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Categoria", style = MaterialTheme.typography.labelMedium)
                    Button(
                        onClick = { showCategoryMenu = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(selectedCategory?.name ?: "Selecione uma categoria")
                    }
                    DropdownMenu(
                        expanded = showCategoryMenu,
                        onDismissRequest = { showCategoryMenu = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        categories.filter {
                            it.type == selectedType.value
                        }.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    selectedCategory = category
                                    showCategoryMenu = false
                                }
                            )
                        }
                    }
                }
            }

            // Date Input
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Data (dd/MM/yyyy)", style = MaterialTheme.typography.labelMedium)
                    FinixTextField(
                        value = dateText,
                        onValueChange = { newDate ->
                            dateText = newDate
                            // Try to parse date
                            try {
                                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
                                val date = formatter.parse(newDate)
                                if (date != null) {
                                    selectedDate = date.time
                                }
                            } catch (e: Exception) {
                                // Invalid format, ignore
                            }
                        },
                        label = "Data",
                        placeholder = "DD/MM/YYYY",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Error Message
            if (submitError.isNotEmpty()) {
                item {
                    Text(
                        submitError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }

            // Submit Button
            item {
                Button(
                    onClick = {
                        // Validate inputs
                        var hasError = false

                        if (amount.isEmpty()) {
                            amountError = "Valor é obrigatório"
                            hasError = true
                        } else {
                            val parsedAmount = amount.replace(",", ".").toDoubleOrNull()
                            if (parsedAmount == null || parsedAmount <= 0) {
                                amountError = "Valor deve ser um número positivo"
                                hasError = true
                            }
                        }

                        if (description.isEmpty()) {
                            descriptionError = "Descrição é obrigatória"
                            hasError = true
                        }

                        if (selectedCategory == null) {
                            submitError = "Selecione uma categoria"
                            hasError = true
                        }

                        // Check if date is in the future
                        if (selectedDate > System.currentTimeMillis()) {
                            submitError = "Data não pode ser no futuro"
                            hasError = true
                        }

                        if (!hasError) {
                            isLoading = true
                            val transaction = Transaction(
                                id = System.currentTimeMillis().toString(),
                                userId = "user_123",
                                categoryId = selectedCategory!!.id,
                                accountId = "default_account",
                                amount = amount.replace(",", ".").toDouble(),
                                description = description,
                                type = selectedType,
                                date = selectedDate,
                                tags = emptyList(),
                                receiptUrl = null,
                                isRecurring = false,
                                recurrenceType = null,
                                syncedWithCloud = false,
                                createdAt = System.currentTimeMillis(),
                                updatedAt = System.currentTimeMillis()
                            )

                            transactionViewModel.addTransaction(transaction)
                            isLoading = false
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    Text("Adicionar Transação")
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
