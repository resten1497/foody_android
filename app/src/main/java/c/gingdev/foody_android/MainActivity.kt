package c.gingdev.foody_android

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import c.gingdev.foody_android.databinding.ActivityMainBinding
import c.gingdev.foody_android.util.permissionVerifier
import c.gingdev.foody_android.vm.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val model = MainActivityViewModel(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding
                = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.run {
            model = this@MainActivity.model
                .also { it.onCreate() }
        }
    }
    override fun onStart() {
        super.onStart()
        model.onStart()
    }
    override fun onPause() {
        super.onPause()
        model.onPaused()
    }
    override fun onDestroy() {
        super.onDestroy()
        model.onDestroy()
    }

    private var permissionVerifier = permissionVerifier(this)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionVerifier.run {
            requestPermissionsGranted(grantResults) { model.permissionsReady() }
            requestPermissionsDenied(grantResults) { model.permissionsDenied() }
        }
    }
}
