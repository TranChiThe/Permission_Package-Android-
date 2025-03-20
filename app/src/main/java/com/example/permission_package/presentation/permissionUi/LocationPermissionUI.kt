package com.example.chat_app.data.permissions.location_permission

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.permission_package.data.permissions.PermissionHandler
import com.example.permission_package.presentation.permissionUtils.PermissionEvent

@Composable
fun LocationPermissionUI(
    modifier: Modifier = Modifier,
    shouldRequest: Boolean,
    onPermissionEvent: (PermissionEvent) -> Unit
) {
    PermissionHandler(
        modifier = modifier,
        permissions = PermissionList().locationPermissions,
        shouldRequest = shouldRequest,
        onPermissionEvent = onPermissionEvent

    )
}

