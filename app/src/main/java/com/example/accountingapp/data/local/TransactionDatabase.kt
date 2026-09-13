package com.example.accountingapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.accountingapp.data.local.dao.AccountDao
import com.example.accountingapp.data.local.dao.TransactionDao
import com.example.accountingapp.data.models.Account
import com.example.accountingapp.data.models.Transaction

@Database(entities = [Transaction::class, Account::class], version = 1, exportSchema = false)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao
}
