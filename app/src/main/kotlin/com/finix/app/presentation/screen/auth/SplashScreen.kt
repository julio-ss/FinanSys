package com.finix.app.presentation.screen.auth

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finix.app.presentation.navigation.Route
import com.finix.app.presentation.theme.Primary
import com.finix.app.presentation.theme.PrimaryLight
import com.finix.app.presentation.theme.Secondary
import com.finix.app.presentation.viewmodel.AuthViewModel
import com.finix.app.presentation.viewmodel.AuthState
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var isAnimating by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isAnimating = true
        delay(1000)
    }

    LaunchedEffect(authViewModel.authState.value) {
        when (authViewModel.authState.value) {
            is AuthState.Authenticated -> {
                navController.navigate(Route.Home.route) {
                    popUpTo(Route.Splash.route) { inclusive = true }
                }
            }
            is AuthState.Unauthenticated -> {
                delay(2000)
                navController.navigate(Route.Login.route) {
                    popUpTo(Route.Splash.route) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Primary, Secondary)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.animateContentSize()
        ) {
            Text(
                text = "Finix",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "Controle Financeiro Inteligente",
                fontSize = 16.sp,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                modifier = Modifier.animateContentSize()
            )
        }
    }
}
