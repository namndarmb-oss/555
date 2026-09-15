package com.mq.notificationbot.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val text: String,
    val timestamp: Long,
    var sent: Boolean = false   // آیا به تلگرام ارسال شده؟
)
