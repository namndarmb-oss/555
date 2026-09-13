package com.example.accountingapp.presentation.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.accountingapp.navigation.Screen
import com.example.accountingapp.presentation.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = koinViewModel()
    val transactions by viewModel.transactions.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("داشبورد حسابداری") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // می‌توانید دیالوگ افزودن تراکنش را باز کنید
            }) {
                Icon(Icons.Default.Add, contentDescription = "افزودن")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Text(
                text = "آخرین تراکنش‌ها",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(transactions.size) { index ->
                    val t = transactions[index]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .clickable {
                                // رفتن به جزئیات تراکنش (در صورت نیاز)
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = t.category, style = MaterialTheme.typography.bodyLarge)
                                Text(text = t.date, style = MaterialTheme.typography.bodySmall)
                            }
                            Text(
                                text = "${t.amount} ریال",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (t.amount >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { navController.navigate(Screen.Accounts.route) }) {
                    Text("حساب‌ها")
                }
                Button(onClick = { navController.navigate(Screen.Transactions.route) }) {
                    Text("تراکنش‌ها")
                }
            }
        }
    }
}
