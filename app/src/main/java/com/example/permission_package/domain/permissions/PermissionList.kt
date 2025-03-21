package com.example.chat_app.data.permissions.location_permission

import android.Manifest

class PermissionList() {
    val locationPermissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val contactPermissions = listOf(
        Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS
    )

    val phoneStatePermissions = listOf(
        Manifest.permission.READ_PHONE_STATE
    )

    val messagePermission = listOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_MMS,
    )

    val cameraPermission = listOf(
        Manifest.permission.CAMERA,
    )

    val microPhonePermission = listOf(
        Manifest.permission.FOREGROUND_SERVICE_MICROPHONE
    )

    val recordAudioPermission = listOf(
        Manifest.permission.RECORD_AUDIO
    )

    val readMediaPermission = listOf(
        Manifest.permission.READ_MEDIA_AUDIO,
        Manifest.permission.READ_MEDIA_VIDEO,
        Manifest.permission.READ_MEDIA_IMAGES,
    )

    val postNotificationPermission = listOf(
        Manifest.permission.POST_NOTIFICATIONS
    )
}