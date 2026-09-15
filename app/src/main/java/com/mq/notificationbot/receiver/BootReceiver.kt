package com.mq.notificationbot.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mq.notificationbot.worker.TelegramWorker
import java.util.concurrent.TimeUnit

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            context?.let {
                // برنامه‌ریزی دوره‌ای برای ارسال پیام‌ها (هر 15 دقیقه)
                val workRequest = PeriodicWorkRequestBuilder<TelegramWorker>(15, TimeUnit.MINUTES)
                    .build()
                WorkManager.getInstance(it).enqueueUniquePeriodicWork(
                    "TelegramSender",
                    ExistingPeriodicWorkPolicy.KEEP,
                    workRequest
                )
            }
        }
    }
}
