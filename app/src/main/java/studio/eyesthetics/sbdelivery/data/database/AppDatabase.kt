package studio.eyesthetics.sbdelivery.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import studio.eyesthetics.sbdelivery.data.database.dao.DishDao
import studio.eyesthetics.sbdelivery.data.database.entities.DishEntity

@Database(entities = [DishEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dishDao(): DishDao
}