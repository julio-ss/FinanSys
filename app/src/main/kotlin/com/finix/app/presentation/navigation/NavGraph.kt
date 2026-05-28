package com.finix.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.finix.app.presentation.screen.auth.LoginScreen
import com.finix.app.presentation.screen.auth.RegisterScreen
import com.finix.app.presentation.screen.auth.SplashScreen
import com.finix.app.presentation.screen.home.DashboardScreen
import com.finix.app.presentation.screen.home.HomeScreen
import com.finix.app.presentation.screen.profile.ProfileScreen

sealed class Route(val route: String) {
    data object Splash : Route("splash")
    data object Login : Route("login")
    data object Register : Route("register")
    data object Home : Route("home")
    data object Dashboard : Route("dashboard")
    data object Profile : Route("profile")
}

@Composable
fun FinixNavGraph(
    navController: NavHostController,
    startDestination: String = Route.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Route.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Route.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Route.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable(Route.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Route.Dashboard.route) {
            DashboardScreen(navController = navController)
        }

        composable(Route.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}
