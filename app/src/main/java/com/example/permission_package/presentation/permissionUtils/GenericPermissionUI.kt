package com.example.chat_app.presentation.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
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
    onPermissionStateChanged: (Boolean) -> Unit,
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
        onPermissionStateChanged(allGranted)
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