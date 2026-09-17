package com.example.mq.telegram.repository

import com.example.mq.telegram.data.models.Message
import com.example.mq.telegram.data.room.MessageDao
import kotlinx.coroutines.flow.Flow

class MainRepository(private val messageDao: MessageDao) {
    suspend fun sendMessage(text: String, senderId: Int) {
        val message = Message(text = text, senderId = senderId)
        messageDao.insert(message)
    }

    fun getAllMessages(): Flow<List<Message>> = messageDao.getAll()
}
