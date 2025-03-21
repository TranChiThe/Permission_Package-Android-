package com.example.permission_package.presentation.screen.Utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.example.chat_app.data.permissions.location_permission.ContactsPermissionUI
import com.example.chat_app.data.permissions.location_permission.LocationPermissionUI
import com.example.chat_app.data.permissions.location_permission.PhoneStatePermissionUI
import com.example.permission_package.presentation.permissionUi.CameraPermissionUI
import com.example.permission_package.presentation.permissionUi.MessagePermissionUI
import com.example.permission_package.presentation.permissionUi.ReadMedialPermissionUI
import com.example.permission_package.presentation.permissionUi.RecordAudioPermissionUI
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequestButton(
    permissions: List<String>,
    permissionType: String,
    buttonText: String,
) {
    val context = LocalContext.current
    var showPermissionUI by remember { mutableStateOf(false) }
    val permissionsState = rememberMultiplePermissionsState(permissions)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { showPermissionUI = true }) {
            Text(text = buttonText)
        }

        if (showPermissionUI) {
            when (permissionType) {
                "location" -> LocationPermissionUI(permissionName = "Location",
                    shouldRequest = showPermissionUI,
                    onPermissionEvent = {
                        showPermissionUI = false
                        showPermissionToast(
                            context, "Location", permissionsState.allPermissionsGranted
                        )
                    })

                "contacts" -> ContactsPermissionUI(permissionName = "Contacts",
                    shouldRequest = showPermissionUI,
                    onPermissionEvent = {
                        showPermissionUI = false
                        showPermissionToast(
                            context, "Contacts", permissionsState.allPermissionsGranted
                        )
                    })

                "phone_state" -> PhoneStatePermissionUI(permissionName = "Phone State",
                    shouldRequest = showPermissionUI,
                    onPermissionEvent = {
                        showPermissionUI = false
                        showPermissionToast(
                            context, "Phone State", permissionsState.allPermissionsGranted
                        )
                    })

                "message" -> MessagePermissionUI(permissionName = "Message",
                    shouldRequest = showPermissionUI,
                    onPermissionEvent = {
                        showPermissionUI = false
                        showPermissionToast(
                            context, "Message", permissionsState.allPermissionsGranted
                        )
                    })

                "camera" -> CameraPermissionUI(permissionName = "Camera",
                    shouldRequest = showPermissionUI,
                    onPermissionEvent = {
                        showPermissionUI = false
                        showPermissionToast(
                            context, "Camera", permissionsState.allPermissionsGranted
                        )
                    })

                "record_audio" -> RecordAudioPermissionUI(permissionName = "Record Audio",
                    shouldRequest = showPermissionUI,
                    onPermissionEvent = {
                        showPermissionUI = false
                        showPermissionToast(
                            context, "Record Audio", permissionsState.allPermissionsGranted
                        )
                    })

                "read_media" -> ReadMedialPermissionUI(permissionName = "Read Media",
                    shouldRequest = showPermissionUI,
                    onPermissionEvent = {
                        showPermissionUI = false
                        showPermissionToast(
                            context, "Read Media", permissionsState.allPermissionsGranted
                        )
                    })
            }
        }
    }
}

fun showPermissionToast(context: Context, permissionName: String, isGranted: Boolean) {
    val message = if (isGranted) {
        "$permissionName Permission Granted ✅"
    } else {
        "$permissionName Permission Denied ❌"
    }
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}