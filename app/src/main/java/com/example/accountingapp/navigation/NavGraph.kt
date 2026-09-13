package com.example.accountingapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.accountingapp.presentation.ui.screens.accounts.AccountsScreen
import com.example.accountingapp.presentation.ui.screens.home.HomeScreen
import com.example.accountingapp.presentation.ui.screens.transactions.TransactionsScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Accounts : Screen("accounts")
    object Transactions : Screen("transactions")
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Accounts.route) { AccountsScreen(navController) }
        composable(Screen.Transactions.route) { TransactionsScreen(navController) }
    }
}
