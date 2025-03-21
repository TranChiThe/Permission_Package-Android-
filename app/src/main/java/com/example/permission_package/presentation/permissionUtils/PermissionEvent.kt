package com.example.permission_package.presentation.permissionUtils

sealed class PermissionEvent {
    object Granted : PermissionEvent()
    object Denied : PermissionEvent()
    object DeniedPermanently : PermissionEvent()
    object NotGranted : PermissionEvent()
    object OnlyThisTime: PermissionEvent()
}