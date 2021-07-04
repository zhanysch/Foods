package kg.appsstudio.di

import dagger.Module
import dagger.Provides
import kg.appsstudio.food_order.repo.Repository
import kg.appsstudio.food_order.repo.RepositoryImpl
import kg.appsstudio.food_order.room.database.AppDatabase
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideOrderRepository(appDataBase: AppDatabase): RepositoryImpl{
        return Repository(appDataBase)
    }
}