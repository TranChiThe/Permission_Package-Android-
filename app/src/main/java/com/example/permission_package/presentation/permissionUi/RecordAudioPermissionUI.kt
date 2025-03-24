package com.example.permission_package.presentation.permissionUi

import com.example.permission_package.data.PermissionList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.permission_package.domain.permissions.PermissionHandler
import com.example.permission_package.presentation.permissionUtils.PermissionEvent

@Composable
fun RecordAudioPermissionUI(
    modifier: Modifier = Modifier,
    permissionName: String,
    shouldRequest: Boolean,
    onPermissionEvent: (PermissionEvent) -> Unit,
    ) {
    PermissionHandler(
        modifier = modifier,
        permissionName = permissionName,
        permissions = PermissionList().recordAudioPermission,
        shouldRequest = shouldRequest,
        onPermissionEvent = onPermissionEvent,
    )
}

