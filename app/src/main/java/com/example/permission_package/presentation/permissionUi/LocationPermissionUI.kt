package com.example.chat_app.presentation.permissions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.chat_app.data.permissions.location_permission.LocationPermissionHandle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionUI(modifier: Modifier = Modifier) {
    var locationText by remember { mutableStateOf("Location: Not granted") }
    var requestLocation by remember { mutableStateOf(true) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val permissionHandle = LocationPermissionHandle(context)
    val permissionsState = rememberMultiplePermissionsState(permissionHandle.locationPermissions)

    val sharedPreferences = remember {
        context.getSharedPreferences("permissions_prefs", Context.MODE_PRIVATE)
    }

    LaunchedEffect(requestLocation) {
        if (requestLocation) {
            permissionsState.launchMultiplePermissionRequest()
        }
    }

    GenericPermissionUI(modifier = modifier,
        permissions = permissionHandle.locationPermissions,
        requestPermissionTrigger = requestLocation,
        onPermissionsGranted = {
            locationText = "Location: Granted"
            sharedPreferences.edit().putBoolean("one_time_permission", false)
                .putBoolean("permanently_denied", false).apply()
            requestLocation = false
            showSettingsDialog = false
        },
        onPermissionsDenied = { denied ->
            locationText = "Location: Denied - $denied"
            val permanentlyDenied = permissionsState.permissions.any {
                !it.status.isGranted && !it.status.shouldShowRationale
            }
            if (permanentlyDenied) {
                sharedPreferences.edit().putBoolean("permanently_denied", true).apply()
                showSettingsDialog = true
                requestLocation = false
            } else {
                sharedPreferences.edit().putBoolean("one_time_permission", true).apply()
                requestLocation = true
            }
        },
        onPermissionStateChanged = { granted ->
            locationText = if (granted) "Location: Granted" else "Location: Not granted"
        })

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(locationText, modifier = Modifier.padding(8.dp))

        // Hiển thị Dialog mở Cài đặt khi quyền bị từ chối vĩnh viễn
        if (showSettingsDialog) {
            AlertDialog(onDismissRequest = { showSettingsDialog = false },
                title = { Text("Permission Required") },
                text = { Text("Please enable location permission in settings to continue.") },
                confirmButton = {
                    Button(onClick = {
                        showSettingsDialog = false
                        openAppSettings(context)
                    }) { Text("Open Settings") }
                },
                dismissButton = {
                    Button(onClick = { showSettingsDialog = false }) { Text("Cancel") }
                })
        }
    }
}

// Hàm mở Cài đặt ứng dụng
fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}
