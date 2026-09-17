package com.example.mq.telegram.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mq.telegram.ui.screens.MainScreen
import com.example.mq.telegram.ui.theme.TelegramAppTheme
import com.example.mq.telegram.viewmodel.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mq.telegram.repository.MainRepository
import com.example.mq.telegram.data.room.AppDatabase

class MainActivity : ComponentActivity() {
    // برای سادگی، شناسهٔ کاربر فعلی را به‌صورت ثابت می‌گذاریم.
    // در پروژهٔ واقعی این مقدار باید از لاگین دریافت شود.
    private val currentUserId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(applicationContext)
        val repository = MainRepository(db.messageDao())
        val viewModelFactory = MainViewModelFactory(repository)

        setContent {
            TelegramAppTheme {
                MainScreen(
                    userId = currentUserId,
                    viewModel = viewModel(factory = viewModelFactory)
                )
            }
        }
    }
}

/** ViewModelFactory برای MainViewModel */
class MainViewModelFactory(
    private val repository: MainRepository
) : androidx.lifecycle.ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}
