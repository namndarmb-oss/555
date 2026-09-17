package com.example.mq.telegram.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mq.telegram.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isRegisterMode by remember { mutableStateOf(false) }

    val loginResult = viewModel.loginResult.collectAsState().value

    LaunchedEffect(loginResult) {
        if (loginResult == true) {
            onLoginSuccess()
        } else if (loginResult == false) {
            // نمایش پیام خطا (به‌صورت ساده)
            // در پروژهٔ واقعی Snackbar یا Dialog استفاده کنید
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(if (isRegisterMode) "ثبت‌نام" else "ورود") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("نام کاربری") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("رمز عبور") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (isRegisterMode) viewModel.register(username, password)
                    else viewModel.login(username, password)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isRegisterMode) "ثبت‌نام" else "ورود")
            }
            TextButton(onClick = { isRegisterMode = !isRegisterMode }) {
                Text(if (isRegisterMode) "قبلاً حساب دارید؟ ورود" else "حساب ندارید؟ ثبت‌نام")
            }
        }
    }
}
