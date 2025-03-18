package com.example.chat_app.presentation.permissions

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.chat_app.data.permissions.location_permission.PermissionHandle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionUI(modifier: Modifier = Modifier, onPermissionGranted: () -> Unit) {
    var requestLocation by remember { mutableStateOf(true) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val permissionHandle = PermissionHandle()
    val permissionsState = rememberMultiplePermissionsState(permissionHandle.locationPermissions)

    val sharedPreferences = remember {
        context.getSharedPreferences("permissions_prefs", Context.MODE_PRIVATE)
    }

    var deniedCount by remember {
        mutableStateOf(
            sharedPreferences.getInt(
                "location_denied_count", 0
            )
        )
    }
    var permanentlyDenied by remember {
        mutableStateOf(
            sharedPreferences.getBoolean(
                "location_permanently_denied", false
            )
        )
    }

    LaunchedEffect(requestLocation) {
        if (requestLocation) {
            permissionsState.launchMultiplePermissionRequest()
        }
    }

    if (permissionsState.allPermissionsGranted) {
        onPermissionGranted()
        sharedPreferences.edit().putInt("location_denied_count", 0)
            .putBoolean("location_permanently_denied", false).apply()
    }

    GenericPermissionUI(
        modifier = modifier,
        permissions = permissionHandle.locationPermissions,
        requestPermissionTrigger = requestLocation,
        onPermissionsGranted = {
            sharedPreferences.edit().putBoolean("location_permanently_denied", false)
                .putInt("location_denied_count", 0).apply()
            requestLocation = false
            showSettingsDialog = false
        },
        onPermissionsDenied = { denied ->
            val permanentlyDeniedNow = permissionsState.permissions.any {
                !it.status.isGranted && !it.status.shouldShowRationale
            }

            if (permanentlyDeniedNow) {
                if (deniedCount > 1) {
                    showSettingsDialog = true
                }

                sharedPreferences.edit().putBoolean("location_permanently_denied", true).apply()
                requestLocation = false
            } else {
                deniedCount += 1
                sharedPreferences.edit().putInt("location_denied_count", deniedCount).apply()

                if (deniedCount >= 2) {
                    requestLocation = false
                } else {
                    requestLocation = true
                }
            }
        },
    )

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomSettingsDialog(showDialog = showSettingsDialog,
            onDismiss = { showSettingsDialog = false },
            onOpenSettings = { openAppSettings(context) })
    }
}
