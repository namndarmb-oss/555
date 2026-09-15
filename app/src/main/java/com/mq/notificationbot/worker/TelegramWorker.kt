package com.mq.notificationbot.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mq.notificationbot.data.repository.NotificationRepository
import com.mq.notificationbot.telegram.TelegramBot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TelegramWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private val repository = NotificationRepository.getInstance(appContext)

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val pending = repository.getPending()
            for (item in pending) {
                val message = "*${item.title}*\n${item.text}"
                val success = TelegramBot.sendMessage(message)
                if (success) {
                    repository.markSent(item.id)
                }
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}
