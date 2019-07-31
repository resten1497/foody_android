package c.gingdev.foody_android.util

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

class permissionVerifier(private val activity: Activity) {
    /*etc.*/
    private fun underMashMellow(f: ()-> Unit) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            f()
    }

    /*Utils*/

    private fun requestUnGranted(f: ()-> Unit, permissions: Array<String>) {
        val unGrantedPermissions: Array<String>
                = findPermissionUngranted(permissions)

        if (unGrantedPermissions.isEmpty()) {
            f(); return
        }
        return ActivityCompat.requestPermissions(activity, permissions, 0)
    }
    private fun findPermissionUngranted(permissions: Array<String>): Array<String> {
        val list: MutableList<String> = ArrayList()
        permissions.forEach {
            if (!isPermissionGranted(it))
                list.add(it)
        }
        return list.toTypedArray()
    }

    private val GRANTED = PackageManager.PERMISSION_GRANTED
    private val DENIED = PackageManager.PERMISSION_DENIED
    private fun isPermissionGranted(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(activity, permission) == GRANTED
    }

    fun checkPermissions(permissions: Array<String>, f: ()-> Unit) {
        underMashMellow { f() }

        requestUnGranted(f, permissions)
    }

    fun requestPermissionsGranted(grantResult: IntArray, f: ()-> Unit) {
        if (DENIED !in grantResult) f()
    }

    fun requestPermissionsDenied(grantResult: IntArray, f: ()-> Unit) {
        if (DENIED in grantResult) f()
    }

    /*Singleton Instance*/

    companion object {
        private var INSTANCE: permissionVerifier? = null

        fun getInstance(activity: Activity): permissionVerifier =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildInstance(activity).also { INSTANCE = it } }

        private fun buildInstance(activity: Activity): permissionVerifier
                = permissionVerifier(activity)
    }
}