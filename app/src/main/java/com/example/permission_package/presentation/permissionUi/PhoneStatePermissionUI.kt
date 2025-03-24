package com.example.chat_app.data.permissions.location_permission

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.permission_package.data.PermissionList
import com.example.permission_package.domain.permissions.PermissionHandler
import com.example.permission_package.presentation.permissionUtils.PermissionEvent

@Composable
fun PhoneStatePermissionUI(
    modifier: Modifier = Modifier,
    permissionName: String,
    shouldRequest: Boolean,
    onPermissionEvent: (PermissionEvent) -> Unit,
    ) {
    PermissionHandler(
        modifier = modifier,
        permissionName = permissionName,
        permissions = PermissionList().phoneStatePermissions,
        shouldRequest = shouldRequest,
        onPermissionEvent = onPermissionEvent,
    )
}

