package com.example.permission_package.data.permissions

import android.content.Context
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
    permissions: List<String>,
    onPermissionEvent: (PermissionEvent) -> Unit,
) {
    var requestPermission by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val permissionsState = rememberMultiplePermissionsState(permissions)

    val sharedPreferences = remember {
        context.getSharedPreferences("permissions_prefs", Context.MODE_PRIVATE)
    }

    LaunchedEffect(Unit) {
        val oneTimePermission = sharedPreferences.getBoolean("one_time_permission", false)
        val onlyThisTime = permissionsState.permissions.any {
            !it.status.isGranted && !it.status.shouldShowRationale
        }
        val isPermanentlyDenied = permissionsState.permissions.any {
            !it.status.isGranted && it.status.shouldShowRationale
        }

        if (permissionsState.allPermissionsGranted) {
            onPermissionEvent(PermissionEvent.Granted)
            sharedPreferences.edit().remove("permanently_denied").apply()
        } else {
            if (isPermanentlyDenied) {
                showSettingsDialog = true
            } else if (oneTimePermission || onlyThisTime) {
                sharedPreferences.edit().remove("permanently_denied").apply()
                sharedPreferences.edit().putBoolean("one_time_permission", true)
                    .putBoolean("permanently_denied", false).apply()
                requestPermission = true
            } else {
                requestPermission = true
            }
        }
    }

    LaunchedEffect(requestPermission) {
        if (requestPermission) {
            val hasInteracted = sharedPreferences.getBoolean("has_interacted", false)
            val permanentlyDenied = sharedPreferences.getBoolean("permanently_denied", false)
            val oneTimePermission = sharedPreferences.getBoolean("one_time_permission", false)

            if (permanentlyDenied && hasInteracted) {
                showSettingsDialog = true
                onPermissionEvent(PermissionEvent.DeniedPermanently)
            } else {
                if (permissionsState.allPermissionsGranted) {
                    sharedPreferences.edit().putBoolean("one_time_permission", false)
                        .putBoolean("permanently_denied", false).putBoolean("has_interacted", true)
                        .apply()
                    onPermissionEvent(PermissionEvent.Granted)
                } else {
                    permissionsState.launchMultiplePermissionRequest()
                    sharedPreferences.edit().putBoolean("has_interacted", true).apply()
                    if (permissionsState.allPermissionsGranted) {
                        sharedPreferences.edit().putBoolean("one_time_permission", false)
                            .putBoolean("permanently_denied", false).apply()
                        onPermissionEvent(PermissionEvent.Granted)
                    } else {
                        val isPermanentlyDenied = permissionsState.permissions.any {
                            !it.status.isGranted && it.status.shouldShowRationale
                        }
                        val onlyThisTime = permissionsState.permissions.any {
                            !it.status.isGranted && !it.status.shouldShowRationale
                        }
                        if (isPermanentlyDenied) {
                            sharedPreferences.edit().putBoolean("one_time_permission", false)
                                .putBoolean("permanently_denied", true).apply()
                            onPermissionEvent(PermissionEvent.DeniedPermanently)
                            showSettingsDialog = true
                        } else if (onlyThisTime) {
                            sharedPreferences.edit().putBoolean("one_time_permission", true)
                                .putBoolean("permanently_denied", false).apply()
                            sharedPreferences.edit().remove("permanently_denied").apply() //
                            onPermissionEvent(PermissionEvent.OnlyThisTime)
                            requestPermission = true
                        } else {
                            onPermissionEvent(PermissionEvent.Denied)
                            requestPermission = true
                        }
                    }
                }
            }
            requestPermission = false
        }
    }

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SettingsDialog(showDialog = showSettingsDialog, onDismiss = {
            showSettingsDialog = false
            onPermissionEvent(PermissionEvent.DeniedPermanently)
        }, openSettings = { openAppSettings(context) })
    }
}
