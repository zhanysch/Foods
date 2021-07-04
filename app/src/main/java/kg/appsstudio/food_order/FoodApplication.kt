package kg.appsstudio.food_order

import android.util.Log
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kg.appsstudio.di.DaggerAppComponent

class FoodApplication : DaggerApplication() {

    companion object {
        const val TAG = "FoodApplication"
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        Log.d(TAG, " >>> FoodApplication Created")

        if (BuildConfig.DEBUG){
            Log.d(TAG, " >>> Initializing Stetho")
            Stetho.initializeWithDefaults(this)
        }

        return DaggerAppComponent.builder().application(this).build()
    }

}