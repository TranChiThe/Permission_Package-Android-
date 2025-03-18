package com.example.permission_package.presentation.permissionUi

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.chat_app.data.permissions.location_permission.PermissionHandle
import com.example.chat_app.presentation.permissions.CustomSettingsDialog
import com.example.chat_app.presentation.permissions.GenericPermissionUI
import com.example.chat_app.presentation.permissions.openAppSettings
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactsPermissionUI(modifier: Modifier = Modifier, onPermissionGranted: () -> Unit) {
    var requestContacts by remember { mutableStateOf(true) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val permissionHandle = PermissionHandle()
    val permissionsState = rememberMultiplePermissionsState(permissionHandle.contactPermissions)

    val sharedPreferences = remember {
        context.getSharedPreferences("permissions_prefs", Context.MODE_PRIVATE)
    }

    // Get the number of rejections from SharedPreferences
    var deniedCount by remember { mutableStateOf(sharedPreferences.getInt("denied_count", 0)) }

    LaunchedEffect(requestContacts) {
        if (requestContacts) {
            permissionsState.launchMultiplePermissionRequest()
        }
    }

    if (permissionsState.allPermissionsGranted) {
        onPermissionGranted()
        sharedPreferences.edit().putInt("denied_count", 0).apply()
    }

    GenericPermissionUI(
        modifier = modifier,
        permissions = permissionHandle.contactPermissions,
        requestPermissionTrigger = requestContacts,
        onPermissionsGranted = {
            sharedPreferences.edit().putBoolean("permanently_denied", false)
                .putInt("denied_count", 0).apply()
            requestContacts = false
            showSettingsDialog = false
        },
        onPermissionsDenied = { denied ->
            val permanentlyDenied = permissionsState.permissions.any {
                !it.status.isGranted && !it.status.shouldShowRationale
            }

            if (permanentlyDenied) {
                sharedPreferences.edit().putBoolean("permanently_denied", true).apply()
                showSettingsDialog = true
                requestContacts = false
            } else {
                deniedCount += 1
                sharedPreferences.edit().putInt("denied_count", deniedCount).apply()

                if (deniedCount >= 2) {
                    showSettingsDialog = true
                } else {
                    requestContacts = false
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

