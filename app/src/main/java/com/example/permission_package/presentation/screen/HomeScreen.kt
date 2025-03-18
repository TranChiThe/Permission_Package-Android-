package com.example.permission_package.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chat_app.presentation.permissions.LocationPermissionUI
import com.example.permission_package.presentation.permissionUi.ContactsPermissionUI

@Composable
fun HomeScreen() {
    var locationGranted by remember { mutableStateOf(false) }
    var contactsGranted by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (!locationGranted) {
            LocationPermissionUI(onPermissionGranted = { locationGranted = true })
        } else if (!contactsGranted) {
            ContactsPermissionUI(onPermissionGranted = { contactsGranted = true })
        } else {
            Text(text = "Oke")
        }
    }
}