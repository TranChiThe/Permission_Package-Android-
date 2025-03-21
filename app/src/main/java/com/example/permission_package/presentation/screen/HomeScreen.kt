import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            permissions = listOf("location"), buttonText = "Request Location Permission"
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Contacts
        PermissionRequestButton(
            permissions = listOf("contacts"), buttonText = "Request Contacts Permission"
        )
        Spacer(modifier = Modifier.height(20.dp))

        //Phone State
        PermissionRequestButton(
            permissions = listOf("phone_state"), buttonText = "Request Phone State Permission"
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Message
        PermissionRequestButton(
            permissions = listOf("message"), buttonText = "Request Message Permission"
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Camera
        PermissionRequestButton(
            permissions = listOf("camera"), buttonText = "Request Camera Permission"
        )
    }
}