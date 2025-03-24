package com.example.permission_package.presentation.screen.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.example.permission_package.data.PermissionList
import com.example.permission_package.presentation.screen.permission.GenericPermissionUI
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequestButton(
    permissions: List<String>,
    permissionType: String,
    permissionName: String,
    buttonText: String,
    onPermissionGranted: () -> Unit,
) {
    val context = LocalContext.current
    var showRequest by remember { mutableStateOf(false) }
    val permissionsState = rememberMultiplePermissionsState(permissions)
    val permissionList = PermissionList()

    val permissions = when (permissionType.lowercase()) {
        "camera" -> permissionList.cameraPermission
        "record_audio" -> permissionList.recordAudioPermission
        "post_notification" -> permissionList.postNotificationPermission
        "contacts" -> permissionList.contactPermissions
        "location" -> permissionList.locationPermissions
        "phone_state" -> permissionList.phoneStatePermissions
        "message" -> permissionList.messagePermission
        "read_media" -> permissionList.readMediaPermission
        else -> emptyList()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { showRequest = true }) {
            Text(text = buttonText)
        }

        if (showRequest) {
            GenericPermissionUI(
                permissionName = permissionName,
                permissions = permissions,
                shouldRequest = showRequest,
                onPermissionEvent = {
                    showRequest = false
                    showPermissionToast(
                        context, permissionName, permissionsState.allPermissionsGranted
                    )
                    onPermissionGranted()
                }
            )
        }
    }
}

fun showPermissionToast(context: Context, permissionName: String, isGranted: Boolean) {
    val message = if (isGranted) {
        "$permissionName Permission Granted ✅"
    } else {
        "$permissionName Permission Denied ❌"
    }
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}