package com.example.mq.telegram.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mq.telegram.repository.MainRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val messages: StateFlow<List<com.example.mq.telegram.data.models.Message>> =
        repository.getAllMessages()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun sendMessage(text: String, senderId: Int) {
        viewModelScope.launch {
            repository.sendMessage(text, senderId)
        }
    }
}
