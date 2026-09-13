package com.example.accountingapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accountingapp.domain.usecase.GetTransactionsUseCase
import com.example.accountingapp.data.models.Transaction
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class TransactionsViewModel @Inject constructor(
    getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {

    val transactions: StateFlow<List<Transaction>> = getTransactionsUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
