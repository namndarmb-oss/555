package com.example.accountingapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accountingapp.domain.usecase.GetAccountsUseCase
import com.example.accountingapp.data.models.Account
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AccountsViewModel @Inject constructor(
    getAccountsUseCase: GetAccountsUseCase
) : ViewModel() {

    val accounts: StateFlow<List<Account>> = getAccountsUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
