import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.chat_app.data.permissions.location_permission.PermissionHandle
import com.example.permission_package.presentation.permissionUtils.PermissionEvent
import com.example.permission_package.presentation.permissionUtils.SettingsDialog
import com.example.permission_package.presentation.permissionUtils.openAppSettings
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionUI(
    modifier: Modifier = Modifier,
    onPermissionEvent: (PermissionEvent) -> Unit,
) {
    var requestLocation by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val permissionHandle = PermissionHandle()
    val permissionsState = rememberMultiplePermissionsState(permissionHandle.locationPermissions)

    val sharedPreferences = remember {
        context.getSharedPreferences("permissions_prefs", Context.MODE_PRIVATE)
    }

    LaunchedEffect(Unit) {
        if (permissionsState.allPermissionsGranted) {
            onPermissionEvent(PermissionEvent.Granted)
        } else {
            val hasInteracted = sharedPreferences.getBoolean("has_interacted", false)
            val permanentlyDenied = sharedPreferences.getBoolean("permanently_denied", false)
            val oneTimePermission = sharedPreferences.getBoolean("one_time_permission", false)
            if (permanentlyDenied && hasInteracted) {
                showSettingsDialog = true
            } else if (oneTimePermission) {
                requestLocation = true
            } else {
                requestLocation = true
            }
        }
    }

    LaunchedEffect(requestLocation) {
        if (requestLocation) {
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
                            onPermissionEvent(PermissionEvent.OnlyThisTime)
                            requestLocation = true
                        } else {
                            onPermissionEvent(PermissionEvent.Denied)
                            requestLocation = true
                        }
                    }
                }
            }
            requestLocation = false
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
