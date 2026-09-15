package com.mq.notificationbot.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.mq.notificationbot.data.repository.NotificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationListener : NotificationListenerService() {

    private val repository by lazy {
        NotificationRepository.getInstance(applicationContext)
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        // می‌توانید لاگ یا کاری دیگر انجام دهید
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        sbn?.let { notification ->
            val extras = notification.notification.extras
            val title = extras.getString("android.title") ?: "بدون عنوان"
            val text = extras.getString("android.text") ?: "بدون متن"

            // ذخیره در دیتابیس (به صورت پس‌زمینه)
            CoroutineScope(Dispatchers.IO).launch {
                repository.saveNotification(title, text)
            }
        }
    }

    // اگر بخواهید نوتیفیکیشن حذف شود هم می‌توانید handle کنید
}
