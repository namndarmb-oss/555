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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mq.notificationbot.R
import com.mq.notificationbot.ui.theme.NotificationBotTheme
import com.mq.notificationbot.util.PermissionHelper

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

private val requestPermissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestPermission()
) { isGranted ->
    // دسترسی اعلان‌ها دریافت یا رد شده است.
}

private val pickImageLauncher = registerForActivityResult(
    ActivityResultContracts.GetContent()
) { uri: Uri? ->
    uri?.let {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)

        prefs.edit()
            .putString("bg_image_uri", it.toString())
            .apply()
    }
}

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // بررسی فعال بودن Notification Listener
    if (!PermissionHelper.isNotificationListenerEnabled(this)) {
        PermissionHelper.openNotificationListenerSettings(this)
    }

    // درخواست اجازه اعلان برای Android 13+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        requestPermissionLauncher.launch(
            Manifest.permission.POST_NOTIFICATIONS
        )
    }

    setContent {
        NotificationBotTheme {

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text("صفحهٔ پس‌زمینه")
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    pickImage()
                                }
                            ) {
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

private fun pickImage() {
    pickImageLauncher.launch("image/*")
}

}

@Composable
fun BackgroundScreen(
modifier: Modifier = Modifier
) {
val context = LocalContext.current

val prefs = remember {
    context.getSharedPreferences(
        "app_prefs",
        Activity.MODE_PRIVATE
    )
}

var imageUriString by remember {
    mutableStateOf(
        prefs.getString("bg_image_uri", null)
    )
}

Box(
    modifier = modifier.fillMaxSize()
) {

    if (imageUriString != null) {

        val painter = rememberAsyncImagePainter(
            model = Uri.parse(imageUriString)
        )

        Image(
            painter = painter,
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

    } else {

        Image(
            painter = rememberAsyncImagePainter(
                model = R.drawable.ic_default_background
            ),
            contentDescription = "Default Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }

    Text(
        text = "اپلیکیشن در حال اجراست...",
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.align(Alignment.Center)
    )
}

}
