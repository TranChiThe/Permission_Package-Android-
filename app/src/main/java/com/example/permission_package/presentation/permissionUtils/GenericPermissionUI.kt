package com.example.chat_app.presentation.permissions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.permission_package.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GenericPermissionUI(
    modifier: Modifier = Modifier,
    permissions: List<String>,
    requestPermissionTrigger: Boolean,
    onPermissionsGranted: () -> Unit,
    onPermissionsDenied: (List<String>) -> Unit,
) {
    val permissionsState = rememberMultiplePermissionsState(permissions)

    // Xử lý yêu cầu quyền khi trigger thay đổi
    LaunchedEffect(requestPermissionTrigger) {
        if (requestPermissionTrigger) {
            if (permissionsState.allPermissionsGranted) {
                onPermissionsGranted()
            } else {
                permissionsState.launchMultiplePermissionRequest()
            }
        }
    }

    // Cập nhật trạng thái quyền khi thay đổi
    LaunchedEffect(permissionsState.allPermissionsGranted) {
        val allGranted = permissionsState.allPermissionsGranted
        if (allGranted) {
            onPermissionsGranted()
        } else {
            val deniedPermissions =
                permissionsState.permissions.filter { !it.status.isGranted }.map { it.permission }
            if (deniedPermissions.isNotEmpty()) {
                onPermissionsDenied(deniedPermissions)
            }
        }
    }

    Text(
        text = if (permissionsState.allPermissionsGranted) "Permissions granted" else "Permissions not granted",
        modifier = modifier
    )
}

@Composable
fun CustomSettingsDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onOpenSettings: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .shadow(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground), // Biểu tượng custom
                        contentDescription = "Permission Icon",
                        tint = Color(0xFF3F51B5),
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Grant access",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "The app needs permissions to function correctly. Please open Settings and grant permissions!",
                        style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(Color.LightGray)
                        ) {
                            Text("Cancel", color = Color.Black)
                        }

                        Button(
                            onClick = {
                                onOpenSettings()
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFF3F51B5))
                        ) {
                            Text("Open Settings", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

// Hàm mở Cài đặt ứng dụng
fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}