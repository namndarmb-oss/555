package com.mq.notificationbot.data.repository

import android.content.Context
import androidx.room.Room
import com.mq.notificationbot.data.db.NotificationDatabase
import com.mq.notificationbot.data.db.NotificationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationRepository private constructor(context: Context) {

    private val db = Room.databaseBuilder(
        context.applicationContext,
        NotificationDatabase::class.java,
        "notification-db"
    ).fallbackToDestructiveMigration().build()

    private val dao = db.notificationDao()

    suspend fun saveNotification(title: String, text: String) {
        val entity = NotificationEntity(
            title = title,
            text = text,
            timestamp = System.currentTimeMillis()
        )
        withContext(Dispatchers.IO) {
            dao.insert(entity)
        }
    }

    suspend fun getPending(): List<NotificationEntity> = withContext(Dispatchers.IO) {
        dao.getUnsentNotifications()
    }

    suspend fun markSent(id: Long) = withContext(Dispatchers.IO) {
        dao.markAsSent(id)
    }

    companion object {
        @Volatile private var INSTANCE: NotificationRepository? = null

        fun getInstance(context: Context): NotificationRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: NotificationRepository(context).also { INSTANCE = it }
            }
        }
    }
}
