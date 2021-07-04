package kg.appsstudio.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kg.appsstudio.food_order.ui.MainActivity

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun mainActivityProvider() : MainActivity
}