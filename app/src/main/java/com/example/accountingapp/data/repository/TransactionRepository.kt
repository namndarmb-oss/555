package com.example.accountingapp.data.repository

import com.example.accountingapp.data.local.dao.TransactionDao
import com.example.accountingapp.data.models.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {
    fun getAllTransactions(): Flow<List<Transaction>> = transactionDao.getAll()
    suspend fun addTransaction(t: Transaction) = transactionDao.insert(t)
    suspend fun removeTransaction(t: Transaction) = transactionDao.delete(t)
}
