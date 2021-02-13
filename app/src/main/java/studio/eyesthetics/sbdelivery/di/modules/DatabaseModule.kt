package studio.eyesthetics.sbdelivery.di.modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import studio.eyesthetics.sbdelivery.data.database.AppDatabase
import studio.eyesthetics.sbdelivery.data.database.dao.CategoriesDao
import studio.eyesthetics.sbdelivery.data.database.dao.DishesDao
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                "exhibitions_database")
            .allowMainThreadQueries()
            .build()
    }
    @Provides
    @Singleton
    internal fun provideCategoriesDao(appDatabase: AppDatabase): CategoriesDao {
        return appDatabase.categoriesDao()
    }

    @Provides
    @Singleton
    internal fun provideDishesDao(appDatabase: AppDatabase): DishesDao {
        return appDatabase.dishDao()
    }
}