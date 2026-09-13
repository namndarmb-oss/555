package com.example.accountingapp.data.repository

import com.example.accountingapp.data.local.dao.AccountDao
import com.example.accountingapp.data.models.Account
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val accountDao: AccountDao
) {
    fun getAllAccounts(): Flow<List<Account>> = accountDao.getAll()
    suspend fun addAccount(a: Account) = accountDao.insert(a)
    suspend fun removeAccount(a: Account) = accountDao.delete(a)
}
