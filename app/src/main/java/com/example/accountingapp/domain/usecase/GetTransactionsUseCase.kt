package com.example.accountingapp.domain.usecase

import com.example.accountingapp.data.repository.TransactionRepository
import com.example.accountingapp.data.models.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<List<Transaction>> = repository.getAllTransactions()
}
