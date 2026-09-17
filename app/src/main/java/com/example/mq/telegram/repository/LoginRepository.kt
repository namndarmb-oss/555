package com.example.mq.telegram.repository

import com.example.mq.telegram.data.models.User
import com.example.mq.telegram.data.room.UserDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.security.MessageDigest

class LoginRepository(private val userDao: UserDao) {

    /** ساده‌سازی: ذخیرهٔ کاربر جدید (ثبت‌نام) */
    suspend fun register(username: String, password: String): Boolean {
        val existing = userDao.findByUsername(username)
        if (existing != null) return false   // کاربر قبلاً وجود دارد
        val hash = hashPassword(password)
        userDao.insert(User(username = username, passwordHash = hash))
        return true
    }

    /** ورود کاربر */
    suspend fun login(username: String, password: String): Boolean {
        val user = userDao.findByUsername(username) ?: return false
        return user.passwordHash == hashPassword(password)
    }

    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val bytes = md.digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
