package com.example.ourbook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ourbook.ui.screen.bookdetail.BookDetailScreen
import com.example.ourbook.ui.screen.home.HomeScreen
import com.example.ourbook.ui.screen.home.HomeViewModel
import com.example.ourbook.ui.screen.login.LoginScreen
import com.example.ourbook.ui.screen.loan.LoanRegisterScreen
import com.example.ourbook.ui.screen.myloans.MyLoansScreen
import com.example.ourbook.ui.screen.notifications.NotificationsScreen
import com.example.ourbook.ui.screen.profile.ProfileScreen
import com.example.ourbook.ui.screen.register.RegisterScreen
import com.example.ourbook.ui.screen.rewards.RewardsScreen
import org.koin.androidx.compose.koinViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object MyLoans : Screen("my_loans")
    object BookDetail : Screen("book_detail/{bookId}") {
        fun create(bookId: Long) = "book_detail/$bookId"
    }
    object LoanRegister : Screen("loan_register/{bookId}") {
        fun create(bookId: Long) = "loan_register/$bookId"
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
                },
                onGoToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Home.route) {
            val homeViewModel: HomeViewModel = koinViewModel()

            HomeScreen(
                onOpenBook = { id -> navController.navigate(Screen.BookDetail.create(id)) },
                onOpenNotifications = { navController.navigate(Screen.Notifications.route) },
                onOpenRewards = { navController.navigate(Screen.Rewards.route) },
                onOpenProfile = { navController.navigate(Screen.Profile.route) },
                onOpenMyLoans = { navController.navigate(Screen.MyLoans.route) },
                onLogout = {
                    homeViewModel.logout() // 🔥 DESLOGA DE VERDADE
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onBack = { navController.popBackStack() } // só voltar, sem botão “Meus Livros”
            )
        }

        composable(Screen.MyLoans.route) {
            MyLoansScreen(
                onBack = { navController.popBackStack() },
                onOpenBook = { bookId ->
                    // Aqui você navega para a tela de detalhe do livro
                    navController.navigate(Screen.BookDetail.create(bookId))
                }
            )
        }

        composable(
            route = Screen.BookDetail.route,
            arguments = listOf(navArgument("bookId") { type = NavType.LongType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getLong("bookId")
                ?: return@composable

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
            arguments = listOf(
                navArgument("bookId") { type = NavType.LongType }
            )
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getLong("bookId")
                ?: return@composable

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
