package com.mq.notificationbot.telegram

import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

object TelegramBot {

    // ⚠️ این مقادیر را با مقادیر واقعی ربات خود جایگزین کنید
    private const val BOT_TOKEN = "8246714234:AAEHy9S2435R3Nqe7oqY8DC_Pm30HzVZ5gU"
    private const val CHAT_ID = "7127088821"

    private val client = OkHttpClient()

    /**
     * ارسال پیام به تلگرام.
     * @return true اگر پیام با موفقیت ارسال شد
     */
    fun sendMessage(message: String): Boolean {
        return try {
            val url = "https://api.telegram.org/bot$BOT_TOKEN/sendMessage"
            val json = JSONObject().apply {
                put("chat_id", CHAT_ID)
                put("text", message)
                put("parse_mode", "Markdown")
            }

            val body = RequestBody.create(
                "application/json".toMediaTypeOrNull(),
                json.toString()
            )
            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

            client.newCall(request).execute().use { response ->
                val success = response.isSuccessful
                if (!success) {
                    Log.e("TelegramBot", "Failed: ${response.code} ${response.message}")
                }
                success
            }
        } catch (e: Exception) {
            Log.e("TelegramBot", "Exception while sending", e)
            false
        }
    }
}
