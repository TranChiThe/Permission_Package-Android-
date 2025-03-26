package com.example.permission_package.domain.permissions

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity

object PermissionHandler {
    fun handlePermissionsResult(
        context: Context,
        permissions: List<String>,
        result: Map<String, Boolean>,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit,
        showRationaleDialog: () -> Unit,
        showSettingsDialog: () -> Unit
    ) {
        val deniedPermissions = permissions.filter { result[it] == false }
        if (deniedPermissions.isEmpty()) {
            onPermissionGranted()
            return
        } else {
            onPermissionDenied()
        }

        val shouldShowRationale = deniedPermissions.any { perm ->
            (context as ComponentActivity).shouldShowRequestPermissionRationale(perm)
        }

        if (shouldShowRationale) {
            showRationaleDialog()
        } else {
            showSettingsDialog()
        }
    }

    fun openAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }

    fun isGpsEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun openLocationSettings(context: Context) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        context.startActivity(intent)
    }
}
