package com.example.accountingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.accountingapp.navigation.NavGraph
import com.example.accountingapp.presentation.ui.theme.AccountingAppTheme

// فراهم‌سازی Local برای دسترسی به دیتابیس در سطوح پایین‌تر
val LocalDatabase = staticCompositionLocalOf<TransactionDatabase> {
    error("Database not provided")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // دریافت دیتابیس از Application
        val db = (application as AccountingApp).database

        setContent {
            CompositionLocalProvider(LocalDatabase provides db) {
                AccountingAppTheme {
                    Surface {
                        NavGraph()
                    }
                }
            }
        }
    }
}
