import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chat_app.data.permissions.location_permission.PermissionList
import com.example.permission_package.presentation.screen.Utils.PermissionRequestButton

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Location
        PermissionRequestButton(
            permissions = PermissionList().locationPermissions,
            permissionType = "location",
            buttonText = "Request Location Permission"
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Contacts
        PermissionRequestButton(
            permissions = PermissionList().contactPermissions,
            permissionType = "contacts",
            buttonText = "Request Contacts Permission"
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Phone State
        PermissionRequestButton(
            permissions = PermissionList().phoneStatePermissions,
            permissionType = "phone_state",
            buttonText = "Request Phone State Permission"
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Message
        PermissionRequestButton(
            permissions = PermissionList().messagePermission,
            permissionType = "message",
            buttonText = "Request Message Permission"
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Camera
        PermissionRequestButton(
            permissions = PermissionList().cameraPermission,
            permissionType = "camera",
            buttonText = "Request Camera Permission"
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Record Audio
        PermissionRequestButton(
            permissions = PermissionList().recordAudioPermission,
            permissionType = "record_audio",
            buttonText = "Request Record Audio Permission"
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Read Media
        PermissionRequestButton(
            permissions = PermissionList().readMediaPermission,
            permissionType = "read_media",
            buttonText = "Request Read Media Permission"
        )
    }
}