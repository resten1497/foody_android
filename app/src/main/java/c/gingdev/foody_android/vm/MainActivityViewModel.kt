package c.gingdev.foody_android.vm

import android.content.Context
import androidx.databinding.ObservableField
import c.gingdev.foody_android.R

class MainActivityViewModel(private val context: Context): BasicViewModel {
    val infoText = ObservableField<String>(context.getString(R.string.WillFindU))

    /*LifeCycle*/
    override fun onCreate() {
//        Check Permission
    }
    override fun onStart() {}
    override fun onPaused() {}
    override fun onDestroy() {}

}