package com.example.ourbook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ourbook.ui.screen.bookdetail.BookDetailScreen
import com.example.ourbook.ui.screen.home.HomeScreen
import com.example.ourbook.ui.screen.login.LoginScreen
import com.example.ourbook.ui.screen.loan.LoanRegisterScreen
import com.example.ourbook.ui.screen.notifications.NotificationsScreen
import com.example.ourbook.ui.screen.rewards.RewardsScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object BookDetail : Screen("book_detail/{bookId}") {
        fun create(bookId: String) = "book_detail/$bookId"
    }
    object LoanRegister : Screen("loan_register/{bookId}") {
        fun create(bookId: String) = "loan_register/$bookId"
    }
    object Notifications : Screen("notifications")
    object Rewards : Screen("rewards")
}

@Composable
fun OurBookNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onOpenBook = { id -> navController.navigate(Screen.BookDetail.create(id)) },
                onOpenNotifications = { navController.navigate(Screen.Notifications.route) },
                onOpenRewards = { navController.navigate(Screen.Rewards.route) }
            )
        }
        composable(
            route = Screen.BookDetail.route,
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("bookId").orEmpty()
            BookDetailScreen(
                bookId = id,
                onBack = { navController.popBackStack() },
                onRegisterLoan = {
                    navController.navigate(Screen.LoanRegister.create(id))
                }
            )
        }
        composable(
            route = Screen.LoanRegister.route,
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("bookId").orEmpty()
            LoanRegisterScreen(
                bookId = id,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Notifications.route) {
            NotificationsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Rewards.route) {
            RewardsScreen(onBack = { navController.popBackStack() })
        }
    }
}
