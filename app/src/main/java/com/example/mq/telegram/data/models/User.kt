package com.example.mq.telegram.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val passwordHash: String   // در پروژهٔ واقعی از hashing مناسب استفاده کنید
)
