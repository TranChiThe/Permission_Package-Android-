package com.example.permission_package.data

import android.Manifest
import android.os.Build

class PermissionList {
    val locationPermissions = mutableListOf<String>().apply {
        add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    val contactPermissions = listOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS
    )

    val phoneStatePermissions = listOf(
        Manifest.permission.READ_PHONE_STATE
    )

    val messagePermission = listOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_MMS
    )

    val cameraPermission = listOf(
        Manifest.permission.CAMERA
    )

    val recordAudioPermission = mutableListOf<String>().apply {
        add(Manifest.permission.RECORD_AUDIO)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // Android 14+
            add(Manifest.permission.FOREGROUND_SERVICE_MICROPHONE)
        }
    }

    val readMediaPermission = mutableListOf<String>().apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            add(Manifest.permission.READ_MEDIA_AUDIO)
            add(Manifest.permission.READ_MEDIA_VIDEO)
            add(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    val postNotificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        emptyList()
    }
}


fun getPermissionCategory(permission: String): String {
    return when (permission) {
        Manifest.permission.CAMERA -> "Camera"
        Manifest.permission.RECORD_AUDIO, Manifest.permission.FOREGROUND_SERVICE_MICROPHONE -> "Microphone"
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION -> "Location"
        Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS -> "Contacts"
        Manifest.permission.READ_PHONE_STATE -> "Phone State"
        Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_MMS -> "Messages"
        Manifest.permission.READ_MEDIA_AUDIO -> "Audio Files"
        Manifest.permission.READ_MEDIA_VIDEO -> "Video Files"
        Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_EXTERNAL_STORAGE -> "Images"
        Manifest.permission.POST_NOTIFICATIONS -> "Notifications"
        else -> permission.substringAfterLast(".")
    }
}