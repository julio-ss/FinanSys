package com.finix.app.presentation.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finix.app.presentation.component.FinixButton
import com.finix.app.presentation.component.FinixOutlinedButton
import com.finix.app.presentation.component.FinixPasswordTextField
import com.finix.app.presentation.component.FinixTextField
import com.finix.app.presentation.navigation.Route
import com.finix.app.presentation.viewmodel.AuthViewModel
import com.finix.app.presentation.viewmodel.AuthState

@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                navController.navigate(Route.Home.route) {
                    popUpTo(Route.Login.route) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                val error = (authState as AuthState.Error).message
                if (error.contains("email", ignoreCase = true)) {
                    emailError = error
                } else {
                    passwordError = error
                }
            }
            else -> {}
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bem-vindo ao Finix",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Controle suas finanças de forma inteligente",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            FinixTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = ""
                },
                label = "Email",
                keyboardType = KeyboardType.Email,
                isError = emailError.isNotEmpty(),
                errorMessage = emailError,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            FinixPasswordTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = ""
                },
                label = "Senha",
                isError = passwordError.isNotEmpty(),
                errorMessage = passwordError,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            FinixButton(
                text = "Entrar",
                onClick = {
                    authViewModel.login(email, password)
                },
                modifier = Modifier.fillMaxWidth(),
                isLoading = authState is AuthState.Loading
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Não tem conta?",
                style = MaterialTheme.typography.bodyMedium
            )

            FinixOutlinedButton(
                text = "Criar Conta",
                onClick = {
                    navController.navigate(Route.Register.route)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
