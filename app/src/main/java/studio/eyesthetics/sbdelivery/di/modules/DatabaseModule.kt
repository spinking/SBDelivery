package studio.eyesthetics.sbdelivery.di.modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import studio.eyesthetics.sbdelivery.data.database.AppDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application) : AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                "exhibitions_database")
            .allowMainThreadQueries()
            .build()
    }
/*    @Provides
    @Singleton
    internal  fun provideExhibitionDao(appDatabase: AppDatabase) : ExhibitionDao {
        return appDatabase.exhibitionDao()
    }*/
}