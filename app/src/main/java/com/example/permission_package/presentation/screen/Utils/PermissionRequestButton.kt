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
import com.example.permission_package.presentation.permissionUi.MessagePermissionUI
import com.example.permission_package.presentation.permissionUtils.PermissionEvent

@Composable
fun PermissionRequestButton(
    permissions: List<String>,
    buttonText: String
) {
    val context = LocalContext.current
    var showPermissionUI by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { showPermissionUI = true }) {
            Text(text = buttonText)
        }

        if (showPermissionUI) {
            permissions.forEach { permission ->
                when (permission) {
                    "location" -> LocationPermissionUI(
                        permissionName = "Location",
                        shouldRequest = showPermissionUI,
                        onPermissionEvent = { event ->
                            showPermissionUI = false
                            showPermissionToast(context, "Location", event)
                        }
                    )

                    "contacts" -> ContactsPermissionUI(
                        permissionName = "Contacts",
                        shouldRequest = showPermissionUI,
                        onPermissionEvent = { event ->
                            showPermissionUI = false
                            showPermissionToast(context, "Contacts", event)
                        }
                    )

                    "phone_state" -> PhoneStatePermissionUI(
                        permissionName = "Phone State",
                        shouldRequest = showPermissionUI,
                        onPermissionEvent = { event ->
                            showPermissionUI = false
                            showPermissionToast(context, "Phone State", event)
                        }
                    )

                    "message" -> MessagePermissionUI(
                        permissionName = "Message",
                        shouldRequest = showPermissionUI,
                        onPermissionEvent = { event ->
                            showPermissionUI = false
                            showPermissionToast(context, "Message", event)
                        }
                    )
                }
            }
        }
    }
}

fun showPermissionToast(context: Context, permissionName: String, event: PermissionEvent) {
    val message = when (event) {
        PermissionEvent.Granted -> "$permissionName Permission Granted ✅"
        PermissionEvent.Denied, PermissionEvent.DeniedPermanently -> "$permissionName Permission Denied ❌"
        PermissionEvent.NotGranted -> ""
        PermissionEvent.OnlyThisTime -> ""
    }
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
