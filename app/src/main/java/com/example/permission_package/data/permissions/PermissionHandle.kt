package com.example.chat_app.data.permissions.location_permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionHandle() {
    val locationPermissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun isPermissionGranted(context: Context): Boolean {
        return locationPermissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    val contactPermissions = listOf(
        Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS
    )

    val phoneStatePermissions = listOf(
        Manifest.permission.READ_PHONE_STATE
    )

}