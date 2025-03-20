package com.example.permission_package.presentation.permissionUtils

sealed class PermissionEvent {
    object NotGranted : PermissionEvent()
    object Granted : PermissionEvent()
    object OnlyThisTime: PermissionEvent()
    object Denied : PermissionEvent()
    object DeniedPermanently : PermissionEvent()
}