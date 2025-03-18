package com.example.chat_app.data.permissions.location_permission

import android.Manifest
import android.content.Context

class LocationPermissionHandle(private val context: Context) {
    // Danh sách quyền vị trí
    val locationPermissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val callPermissions = listOf(
        Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS
    )
}