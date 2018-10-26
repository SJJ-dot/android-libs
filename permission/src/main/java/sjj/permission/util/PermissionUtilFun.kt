package sjj.permission.util

import android.app.Activity
import android.content.pm.PackageManager
import sjj.permission.PermissionCallback
import sjj.permission.model.Permission
import java.util.concurrent.atomic.AtomicInteger

fun requestPermissions(activity: Activity, onGranted: () -> Unit = {}, onDenied: (List<Permission>) -> Unit = {}, vararg permissions: String) {
    PermissionUtil.requestPermissions(activity,permissions,object :PermissionCallback{
        val list = mutableListOf<Permission>()
        var count = AtomicInteger()
        override fun onGranted(p: Permission) {
            if (count.incrementAndGet() == permissions.size) {
                if (list.isEmpty()) {
                    onGranted()
                } else {
                    onDenied(list)
                }
            }
        }

        override fun onDenied(p: Permission) {
            list.add(p)
            if (count.incrementAndGet() == permissions.size) {
                onDenied(list)
            }
        }
    })
}

fun requestPermissionAll(activity: Activity, onGranted: () -> Unit = {}, onDenied: (List<Permission>) -> Unit = {}) {
    requestPermissions(activity, onGranted, onDenied,*activity.packageManager.getPackageInfo(activity.packageName,PackageManager.GET_PERMISSIONS).requestedPermissions)
}