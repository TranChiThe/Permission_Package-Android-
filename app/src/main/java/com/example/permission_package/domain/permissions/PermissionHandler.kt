package com.example.permission_package.domain.permissions

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.permission_package.presentation.permissionUtils.PermissionEvent
import com.example.permission_package.presentation.permissionUtils.SettingsDialog
import com.example.permission_package.presentation.permissionUtils.openAppSettings
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionHandler(
    modifier: Modifier = Modifier,
    permissionName: String,
    permissions: List<String>,
    shouldRequest: Boolean,
    onPermissionEvent: (PermissionEvent) -> Unit,
) {
    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("permissions_prefs", Context.MODE_PRIVATE)
    }
    val permissionsState = rememberMultiplePermissionsState(permissions)
    val hasInteracted = sharedPreferences.getBoolean("has_interacted", false)

    var requestPermission by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val wasOneTimePermission = sharedPreferences.getBoolean("one_time_permission", false)
    val wasPermanentlyDenied = sharedPreferences.getBoolean("permanently_denied", false)

    val isOneTimePermission = permissionsState.permissions.any {
        !it.status.isGranted && !it.status.shouldShowRationale
    }
    val isPermanentlyDenied = permissionsState.permissions.any {
        !it.status.isGranted && it.status.shouldShowRationale
    }

    val isOneTimePermissionLost = wasOneTimePermission && permissionsState.permissions.any {
        !it.status.isGranted
    }

    LaunchedEffect(shouldRequest, permissionsState.allPermissionsGranted) {
        if (!shouldRequest) return@LaunchedEffect
        if (permissionsState.allPermissionsGranted) {
            onPermissionEvent(PermissionEvent.Granted)
            sharedPreferences.edit().remove("permanently_denied").apply()
        } else {
            if (isPermanentlyDenied) {
                showSettingsDialog = true
            } else if (isOneTimePermissionLost) {
                sharedPreferences.edit().putBoolean("one_time_permission", false)
                    .putBoolean("permanently_denied", false).apply()
                onPermissionEvent(PermissionEvent.OnlyThisTime)
                requestPermission = true
            } else {
                requestPermission = true
            }
        }
    }

    LaunchedEffect(requestPermission) {
        if (requestPermission) {
            if (wasPermanentlyDenied && hasInteracted) {
                showSettingsDialog = true
                onPermissionEvent(PermissionEvent.DeniedPermanently)
            } else {
                permissionsState.launchMultiplePermissionRequest()
                sharedPreferences.edit().putBoolean("has_interacted", true).apply()
                if (permissionsState.allPermissionsGranted) {
                    sharedPreferences.edit().putBoolean("one_time_permission", false)
                        .putBoolean("permanently_denied", false).apply()
                    onPermissionEvent(PermissionEvent.Granted)
                } else {
                    if (isPermanentlyDenied) {
                        sharedPreferences.edit().putBoolean("permanently_denied", true).apply()
                        onPermissionEvent(PermissionEvent.DeniedPermanently)
                        showSettingsDialog = true
                    } else if (isOneTimePermission) {
                        sharedPreferences.edit().putBoolean("one_time_permission", true).apply()
                        onPermissionEvent(PermissionEvent.OnlyThisTime)
                    } else {
                        onPermissionEvent(PermissionEvent.Denied)
                    }
                }
            }
            requestPermission = false
        }
    }

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SettingsDialog(permissionName = permissionName,
            showDialog = showSettingsDialog,
            onDismiss = {
                showSettingsDialog = false
                onPermissionEvent(PermissionEvent.DeniedPermanently)
            },
            openSettings = { openAppSettings(context) })
    }
}


