package kg.appsstudio.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import kg.appsstudio.food_order.FoodApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        RoomModule::class,
        RepositoryModule::class
    ]
)

interface AppComponent : AndroidInjector<FoodApplication> {


    @Component.Builder
    interface  Builder{
        @BindsInstance
        fun application(application: Application) : Builder

        fun build(): AppComponent
    }

}