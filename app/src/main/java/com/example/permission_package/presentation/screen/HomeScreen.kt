import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.chat_app.data.permissions.location_permission.LocationPermissionUI
import com.example.permission_package.presentation.permissionUtils.PermissionEvent
import android.widget.Toast
import com.example.chat_app.data.permissions.location_permission.ContactsPermissionUI
import com.example.chat_app.data.permissions.location_permission.PhoneStatePermissionUI

@Composable
fun HomeScreen() {
    val context = LocalContext.current

    var showLocationUI by remember { mutableStateOf(false) }
    var permissionLocationResult by remember { mutableStateOf<String?>(null) }
    var showContactsUI by remember { mutableStateOf(false) }
    var permissionContactsResult by remember { mutableStateOf<String?>(null) }
    var showPhoneStateUI by remember { mutableStateOf(false) }
    var permissionPhoneStateResult by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Location
        Button(onClick = {
            showLocationUI = true
        }) {
            Text("Request Location Permission")
        }
        permissionLocationResult?.let {
            Text(it, modifier = Modifier.padding(8.dp))
        }
        if (showLocationUI) {
            LocationPermissionUI(
                permissionName = "location",
                onPermissionEvent = { event ->
                    permissionLocationResult = when (event) {
                        PermissionEvent.Granted -> {
                            Toast.makeText(
                                context, "Location Permission Granted", Toast.LENGTH_SHORT).show()
                            "Permission Granted"
                        }

                        PermissionEvent.NotGranted, PermissionEvent.OnlyThisTime, PermissionEvent.Denied, PermissionEvent.DeniedPermanently -> {
                            Toast.makeText(
                                context, "Location Permission Not Granted", Toast.LENGTH_SHORT
                            ).show()
                            "Permission Not Granted"
                        }
                    }
                    showLocationUI = false
                }, shouldRequest = showLocationUI
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Contacts
        Button(onClick = {
            showContactsUI = true
        }) {
            Text("Request Contacts Permission")
        }
        permissionContactsResult?.let {
            Text(it, modifier = Modifier.padding(8.dp))
        }
        if (showContactsUI) {
            ContactsPermissionUI(
                permissionName = "contacts",
                onPermissionEvent = { event ->
                    permissionContactsResult = when (event) {
                        PermissionEvent.Granted -> {
                            Toast.makeText(
                                context, "Contacts Permission Granted", Toast.LENGTH_SHORT).show()
                            "Permission Granted"
                        }

                        PermissionEvent.NotGranted, PermissionEvent.OnlyThisTime, PermissionEvent.Denied, PermissionEvent.DeniedPermanently -> {
                            Toast.makeText(
                                context, "Contacts Permission Not Granted", Toast.LENGTH_SHORT
                            ).show()
                            "Contacts Not Granted"
                        }
                    }
                    showContactsUI = false
                }, shouldRequest = showContactsUI
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Phone State
        Button(onClick = {
            showPhoneStateUI = true
        }) {
            Text("Request Contacts Permission")
        }
        permissionPhoneStateResult?.let {
            Text(it, modifier = Modifier.padding(8.dp))
        }
        if (showPhoneStateUI) {
            PhoneStatePermissionUI (
                permissionName = "phone state",
                onPermissionEvent = { event ->
                    permissionPhoneStateResult = when (event) {
                        PermissionEvent.Granted -> {
                            Toast.makeText(
                                context, "Contacts Permission Granted", Toast.LENGTH_SHORT).show()
                            "Permission Granted"
                        }

                        PermissionEvent.NotGranted, PermissionEvent.OnlyThisTime, PermissionEvent.Denied, PermissionEvent.DeniedPermanently -> {
                            Toast.makeText(
                                context, "Contacts Permission Not Granted", Toast.LENGTH_SHORT
                            ).show()
                            "Contacts Not Granted"
                        }
                    }
                    showPhoneStateUI = false
                }, shouldRequest = showPhoneStateUI
            )
        }
    }
}