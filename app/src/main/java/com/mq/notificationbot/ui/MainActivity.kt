package com.mq.notificationbot.ui

import android.Manifest
import android.app.Activity
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mq.notificationbot.R
import com.mq.notificationbot.util.PermissionHelper
import com.mq.notificationbot.ui.theme.NotificationBotTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // اگر کاربر دسترسی را رد کرد، می‌توانید دوباره درخواست کنید یا پیام بدهید
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // بررسی دسترسی به نوتیفیکیشن‌ها؛ اگر فعال نیست، کاربر را به تنظیمات می‌بریم
        if (!PermissionHelper.isNotificationListenerEnabled(this)) {
            PermissionHelper.openNotificationListenerSettings(this)
        }

        // برای Android 13+ درخواست POST_NOTIFICATIONS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            NotificationBotTheme {
                Scaffold(
                    topBar = {
                        SmallTopAppBar(
                            title = { Text("صفحهٔ پس‌زمینه") },
                            actions = {
                                IconButton(onClick = { pickImage() }) {
                                    Icon(
                                        imageVector = Icons.Filled.Image,
                                        contentDescription = "انتخاب تصویر"
                                    )
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    BackgroundScreen(
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }

    // انتخاب تصویر از گالری
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // ذخیره مسیر در SharedPreferences برای استفاده در Compose
            val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
            prefs.edit().putString("bg_image_uri", it.toString()).apply()
        }
    }

    private fun pickImage() {
        pickImageLauncher.launch("image/*")
    }
}

@Composable
fun BackgroundScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("app_prefs", Activity.MODE_PRIVATE)
    var imageUriString by remember { mutableStateOf(prefs.getString("bg_image_uri", null)) }

    // وقتی کاربر تصویر جدیدی انتخاب می‌کند، UI به‌روزرسانی می‌شود
    LaunchedEffect(imageUriString) {
        // هیچ کاری لازم نیست؛ فقط برای رفرش
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (imageUriString != null) {
            val painter = rememberAsyncImagePainter(model = Uri.parse(imageUriString))
            Image(
                painter = painter,
                contentDescription = "Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // تصویر پیش‌فرض (می‌توانید یک drawable وکتور اضافه کنید)
            Image(
                painter = rememberAsyncImagePainter(model = R.drawable.ic_default_background),
                contentDescription = "Default Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // متن یا المان‌های ساده می‌تواند در مرکز باشد (اختیاری)
        Text(
            text = "اپلیکیشن در حال اجراست...",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
