package com.example.permission_package.presentation.dialog_setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

val PrimaryBlue = Color(0xFF42A5F5)
val SurfaceWhite = Color(0xFFFFFFFF)
val OnPrimaryWhite = Color(0xFFFFFFFF)
val TextGray = Color(0xFF666666)
@Composable
fun PermissionSetting(
    permissionName: String,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onRequestPermissionAgain: () -> Unit,
    title: String = "Why We Need This Permission?",
    message: String = "To ensure the best experience, please grant $permissionName access. This allows us to serve you better!",
    requestButtonText: String = "Go to settings",
    dismissButtonText: String = "Not Now",
    primaryColor: Color = PrimaryBlue,
    surfaceColor: Color = SurfaceWhite,
    textColor: Color = TextGray
) {
    if (showDialog) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(surfaceColor),
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = message,
                        fontSize = 16.sp,
                        color = textColor,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            onRequestPermissionAgain()
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor,
                            contentColor = OnPrimaryWhite
                        )
                    ) {
                        Text(
                            text = requestButtonText,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    TextButton(
                        onClick = { onDismiss() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = dismissButtonText,
                            fontSize = 16.sp,
                            color = primaryColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}