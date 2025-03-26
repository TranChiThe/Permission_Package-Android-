package com.example.permission_package.presentation.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.permission_package.data.getPermissionCategory
import com.example.permission_package.domain.permissions.PermissionHandler
import com.example.permission_package.presentation.dialog_setting.PermissionSetting


@Composable
fun PermissionRequestButton(
    modifier: Modifier = Modifier,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    textColor: Color = Color.White,
    permissions: List<String>,
    buttonText: String,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    permissionName: String,
) {
    val context = LocalContext.current
    var pendingPermissions by remember { mutableStateOf<List<String>?>(null) }
    var shouldRequestAgain by remember { mutableStateOf(false) }

    var showRationaleDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var deniedPermissions by remember { mutableStateOf<List<String>>(emptyList()) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        pendingPermissions?.let { perms ->
            val deniedList = perms.filter { result[it] == false }
            deniedPermissions = deniedList
            PermissionHandler.handlePermissionsResult(context,
                perms,
                result,
                onPermissionGranted,
                onPermissionDenied,
                showRationaleDialog = { showRationaleDialog = true },
                showSettingsDialog = { showSettingsDialog = true })
        }
    }

    LaunchedEffect(shouldRequestAgain) {
        if (shouldRequestAgain) {
            shouldRequestAgain = false
            pendingPermissions?.let { permissionLauncher.launch(it.toTypedArray()) }
        }
    }

    Button(
        modifier = modifier,
        colors = buttonColors,
        onClick = {
            pendingPermissions = permissions
            permissionLauncher.launch(permissions.toTypedArray())
        },
    ) {
        Text(buttonText, color = textColor)
    }

    if (showRationaleDialog) {
        val groupedPermissions = deniedPermissions
            .map { getPermissionCategory(it) }
            .distinct()
        val deniedPermissionsText = groupedPermissions.joinToString(", ") { "$it" }
        PermissionSetting(
            permissionName = permissionName,
            showDialog = showRationaleDialog,
            onDismiss = {
                showRationaleDialog = false
            },
            onRequestPermissionAgain = {
                showRationaleDialog = false
                permissionLauncher.launch(permissions.toTypedArray())
            },
            title = "Permission Required",
            message = "The following permissions are required:$deniedPermissionsText",
            requestButtonText = "Request again",
            dismissButtonText = "Cancel"
        )
    }

    if (showSettingsDialog) {
        val groupedPermissions = deniedPermissions
            .map { getPermissionCategory(it) }
            .distinct()
        val deniedPermissionsText = groupedPermissions.joinToString(", ") { "$it" }
        PermissionSetting(
            permissionName = permissionName,
            showDialog = showSettingsDialog,
            message = "To ensure the best experience, please grant $deniedPermissionsText access. \nThis allows us to serve you better!",
            onDismiss = {
                showSettingsDialog = false
            },
            onRequestPermissionAgain = {
                PermissionHandler.openAppSettings(context)
                showSettingsDialog = false
            },
        )
    }
}
