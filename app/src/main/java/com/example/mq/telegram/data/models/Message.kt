package com.example.mq.telegram.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val senderId: Int,
    val timestamp: Long = System.currentTimeMillis()
)
