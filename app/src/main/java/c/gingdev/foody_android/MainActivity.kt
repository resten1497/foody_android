package c.gingdev.foody_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import c.gingdev.foody_android.databinding.ActivityMainBinding
import c.gingdev.foody_android.vm.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val model = MainActivityViewModel(applicationContext)
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
}
