package c.gingdev.foody_android.vm

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Handler
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import c.gingdev.foody_android.R
import c.gingdev.foody_android.model.accessCoarseLocation
import c.gingdev.foody_android.model.accessFineLocation
import c.gingdev.foody_android.util.permissionVerifier

class MainActivityViewModel(private val context: Context): BasicViewModel {

    /*LifeCycle*/
    override fun onCreate() {
        setTexts(context.getString(R.string.onChecking)
            , context.getString(R.string.onCheckingSub))

        Handler().postDelayed({
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                (context as Activity).requestPermissions(arrayOf(accessFineLocation, accessCoarseLocation), 0)
            else
                permissionsReady()
        }, 1000)
    }
    override fun onStart() {}
    override fun onPaused() {}
    override fun onDestroy() {}

    /*etc.*/

    fun screenTouched() {
        when(permissionState.get()) {
            true -> {
                setTexts("개발중", "개발중")
            }
            false -> {
                checkPermissions()
            }
        }
    }

    /*permissions*/
    val permissionState = ObservableBoolean(false)
    fun permissionsReady() {
        permissionState.set(true)
        setTexts(context.getString(R.string.WillFindU)
            , context.getString(R.string.WillFindUSub))
    }
    fun permissionsDenied() {
        permissionState.set(false)
        setTexts(context.getString(R.string.noPermission)
            , context.getString(R.string.noPermissionSub))
    }

    private var permissionVerifier: permissionVerifier? = null
    fun checkPermissions() {
        permissionVerifier = c.gingdev.foody_android.util.permissionVerifier.getInstance(context as Activity)
        permissionVerifier!!.checkPermissions(arrayOf(accessFineLocation, accessCoarseLocation)) {
            permissionsReady()
        }
    }

    /*TextView*/
    val infoText = ObservableField<String>()
    val subText = ObservableField<String>()
    private fun setTexts(info: String, sub: String) {
        infoText.set(info)
        subText.set(sub)
    }
}