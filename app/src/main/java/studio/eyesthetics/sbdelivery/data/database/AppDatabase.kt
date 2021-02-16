package studio.eyesthetics.sbdelivery.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import studio.eyesthetics.sbdelivery.data.database.dao.CategoriesDao
import studio.eyesthetics.sbdelivery.data.database.dao.DishesDao
import studio.eyesthetics.sbdelivery.data.database.dao.RecommendIdDao
import studio.eyesthetics.sbdelivery.data.database.entities.CategoryEntity
import studio.eyesthetics.sbdelivery.data.database.entities.DishEntity
import studio.eyesthetics.sbdelivery.data.database.entities.RecommendIdEntity

@Database(entities = [DishEntity::class, CategoryEntity::class, RecommendIdEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dishDao(): DishesDao
    abstract fun categoriesDao(): CategoriesDao
    abstract fun recommendIdDao(): RecommendIdDao
}