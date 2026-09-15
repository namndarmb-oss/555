package com.mq.notificationbot.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Insert
    suspend fun insert(notification: NotificationEntity)

    @Query("SELECT * FROM notifications WHERE sent = 0")
    suspend fun getUnsentNotifications(): List<NotificationEntity>

    @Query("UPDATE notifications SET sent = 1 WHERE id = :id")
    suspend fun markAsSent(id: Long)
}
