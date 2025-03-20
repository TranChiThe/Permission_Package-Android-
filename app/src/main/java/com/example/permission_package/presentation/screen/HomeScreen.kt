import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chat_app.data.permissions.location_permission.LocationPermissionUI
import com.example.permission_package.presentation.permissionUtils.PermissionEvent

@Composable
fun HomeScreen() {
    var showLocationUI by remember { mutableStateOf(false) }
    var permissionResult by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            showLocationUI = true
        }) {
            Text("Request Location Permission")
        }
        permissionResult?.let {
            Text(it, modifier = Modifier.padding(8.dp))
        }
        if (showLocationUI) {
            LocationPermissionUI(onPermissionEvent = { event ->
                permissionResult = when (event) {
                    PermissionEvent.NotGranted -> "Permission not granted"
                    PermissionEvent.Granted -> "Permission Granted"
                    PermissionEvent.OnlyThisTime -> "Permission Only This Time"
                    PermissionEvent.Denied -> "Permission Denied"
                    PermissionEvent.DeniedPermanently -> "Permission Denied Permanently"
                }
                showLocationUI = false
            })
        }
    }
}