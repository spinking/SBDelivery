package studio.eyesthetics.sbdelivery.di.modules

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import studio.eyesthetics.sbdelivery.data.database.AppDatabase
import studio.eyesthetics.sbdelivery.data.database.dao.*
import studio.eyesthetics.sbdelivery.data.database.entities.BasketEntity
import studio.eyesthetics.sbdelivery.data.database.ioThread
import javax.inject.Singleton

@Module
class DatabaseModule {

    lateinit var appDatabase: AppDatabase

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase {
        appDatabase = Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                "exhibitions_database")
            .allowMainThreadQueries()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    ioThread {
                        appDatabase.basketDao().insert(BasketEntity(1L, "", "", 0))
                    }
                }
            })
            .build()
        return appDatabase
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

    @Provides
    @Singleton
    internal fun provideRecommendIdDao(appDatabase: AppDatabase): RecommendIdDao {
        return appDatabase.recommendIdDao()
    }

    @Provides
    @Singleton
    internal fun provideDishPersonalInfoDao(appDatabase: AppDatabase): DishPersonalInfoDao {
        return appDatabase.dishPersonalInfoDao()
    }

    @Provides
    @Singleton
    internal fun provideSuggestionsDao(appDatabase: AppDatabase): SuggestionsDao {
        return appDatabase.suggestionsDao()
    }

    @Provides
    @Singleton
    internal fun provideReviewsDao(appDatabase: AppDatabase): ReviewsDao {
        return appDatabase.reviewsDao()
    }

    @Provides
    @Singleton
    internal fun provideBasketDao(appDatabase: AppDatabase): BasketDao {
        return appDatabase.basketDao()
    }

    @Provides
    @Singleton
    internal fun provideBasketItemDao(appDatabase: AppDatabase): BasketItemDao {
        return appDatabase.basketItemDao()
    }
}