package com.example.accountingapp.domain.usecase

import com.example.accountingapp.data.repository.AccountRepository
import com.example.accountingapp.data.models.Account
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    operator fun invoke(): Flow<List<Account>> = repository.getAllAccounts()
}
