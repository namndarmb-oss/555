package com.example.accountingapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,          // می‌توانید به‌جای String از LocalDate استفاده کنید
    val amount: Double,
    val category: String,
    val accountId: Int
)
