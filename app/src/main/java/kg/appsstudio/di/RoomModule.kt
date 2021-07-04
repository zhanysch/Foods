package kg.appsstudio.di

import android.app.Application
import dagger.Module
import dagger.Provides
import kg.appsstudio.food_order.room.database.AppDatabase
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDataBase(application: Application):AppDatabase{
        return AppDatabase.getInstance(application)!!
    }
}