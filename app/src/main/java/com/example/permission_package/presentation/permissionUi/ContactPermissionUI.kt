package com.example.chat_app.data.permissions.location_permission

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.permission_package.domain.permissions.PermissionHandler
import com.example.permission_package.presentation.permissionUtils.PermissionEvent

@Composable
fun ContactsPermissionUI(
    modifier: Modifier = Modifier,
    permissionName: String,
    shouldRequest: Boolean,
    onPermissionEvent: (PermissionEvent) -> Unit,
) {
    PermissionHandler(
        modifier = modifier,
        permissionName = permissionName,
        permissions = PermissionList().contactPermissions,
        shouldRequest = shouldRequest,
        onPermissionEvent = onPermissionEvent
    )
}

