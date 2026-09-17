package com.example.mq.telegram.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mq.telegram.data.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun findByUsername(username: String): User?

    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<User>>
}
