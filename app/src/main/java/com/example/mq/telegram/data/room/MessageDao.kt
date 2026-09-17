package com.example.mq.telegram.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mq.telegram.data.models.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert
    suspend fun insert(message: Message)

    @Query("SELECT * FROM messages ORDER BY timestamp ASC")
    fun getAll(): Flow<List<Message>>
}
