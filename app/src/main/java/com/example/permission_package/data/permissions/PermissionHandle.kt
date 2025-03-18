package com.example.chat_app.data.permissions.location_permission

import android.Manifest

class PermissionHandle() {
    val locationPermissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val contactPermissions = listOf(
        Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS
    )

}