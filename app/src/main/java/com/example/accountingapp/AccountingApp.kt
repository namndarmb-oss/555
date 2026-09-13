package com.example.accountingapp

import android.app.Application
import androidx.room.Room

class AccountingApp : Application() {
    // Singleton دسترسی به دیتابیس
    val database by lazy {
        Room.databaseBuilder(
            this,
            TransactionDatabase::class.java,
            "accounting_db"
        ).fallbackToDestructiveMigration()
         .build()
    }
}
