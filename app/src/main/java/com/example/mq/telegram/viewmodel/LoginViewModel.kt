package com.example.mq.telegram.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mq.telegram.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _loginResult = MutableStateFlow<Boolean?>(null)
    val loginResult: StateFlow<Boolean?> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val success = repository.login(username, password)
            _loginResult.value = success
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            val success = repository.register(username, password)
            _loginResult.value = success
        }
    }
}
