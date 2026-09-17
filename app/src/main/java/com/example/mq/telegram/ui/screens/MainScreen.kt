package com.example.mq.telegram.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mq.telegram.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    userId: Int,
    viewModel: MainViewModel = viewModel()
) {
    var newMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("چت") })
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = newMessage,
                    onValueChange = { newMessage = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("پیام جدید") }
                )
                Button(
                    onClick = {
                        if (newMessage.isNotBlank()) {
                            viewModel.sendMessage(newMessage, userId)
                            newMessage = ""
                        }
                    }
                ) {
                    Text("ارسال")
                }
            }
        }
    ) { padding ->
        val messages by viewModel.messages.collectAsState()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(messages) { message ->
                MessageBubble(message = message, isOwn = message.senderId == userId)
            }
        }
    }
}

@Composable
fun MessageBubble(message: com.example.mq.telegram.data.models.Message, isOwn: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = if (isOwn) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = if (isOwn) MaterialTheme.colorScheme.primary else Color.LightGray,
            tonalElevation = 2.dp,
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = message.text,
                color = if (isOwn) MaterialTheme.colorScheme.onPrimary else Color.Black,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
