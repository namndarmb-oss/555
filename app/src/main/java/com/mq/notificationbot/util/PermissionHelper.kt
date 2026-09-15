package com.mq.notificationbot.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

object PermissionHelper {

    /**
     * باز کردن صفحه تنظیمات برای فعال‌سازی دسترسی به نوتیفیکیشن‌ها
     */
    fun openNotificationListenerSettings(context: Context) {
        val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    /**
     * بررسی اینکه آیا سرویس Listener فعال است یا نه
     */
    fun isNotificationListenerEnabled(context: Context): Boolean {
        val enabledListeners = Settings.Secure.getString(
            context.contentResolver,
            "enabled_notification_listeners"
        ) ?: return false
        val componentName = android.content.ComponentName(context, com.mq.notificationbot.service.NotificationListener::class.java)
        return enabledListeners.contains(componentName.flattenToString())
    }
}
