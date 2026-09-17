package com.example.mq.telegram.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mq.telegram.ui.screens.LoginScreen
import com.example.mq.telegram.ui.theme.TelegramAppTheme
import com.example.mq.telegram.viewmodel.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mq.telegram.repository.LoginRepository
import com.example.mq.telegram.data.room.AppDatabase

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ساخت Repository/ ViewModel به‌صورت ساده (در پروژهٔ واقعی می‌توانید Hilt یا Koin استفاده کنید)
        val db = AppDatabase.getInstance(applicationContext)
        val repository = LoginRepository(db.userDao())
        val viewModelFactory = LoginViewModelFactory(repository)

        setContent {
            TelegramAppTheme {
                LoginScreen(
                    onLoginSuccess = {
                        // پس از ورود موفق به MainActivity می‌رویم
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    },
                    viewModel = viewModel(factory = viewModelFactory)
                )
            }
        }
    }
}

/** ViewModelFactory برای تزریق Repository به ViewModel */
class LoginViewModelFactory(
    private val repository: LoginRepository
) : androidx.lifecycle.ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}
