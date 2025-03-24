package com.example.permission_package.presentation.screen.permission

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.permission_package.domain.permissions.PermissionHandler
import com.example.permission_package.presentation.permissionUtils.PermissionEvent

@Composable
fun GenericPermissionUI(
    modifier: Modifier = Modifier,
    permissionName: String,
    permissions: List<String>,
    shouldRequest: Boolean,
    onPermissionEvent: (PermissionEvent) -> Unit
) {
    PermissionHandler(
        modifier = modifier,
        permissionName = permissionName,
        permissions = permissions,
        shouldRequest = shouldRequest,
        onPermissionEvent = onPermissionEvent
    )
}