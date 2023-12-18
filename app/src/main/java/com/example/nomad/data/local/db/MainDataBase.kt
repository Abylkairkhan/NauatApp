package com.example.nomad.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nomad.data.local.dao.FoodTypeDao
import com.example.nomad.data.local.dao.MainMenuDao
import com.example.nomad.data.local.dao.ProductDao
import com.example.nomad.data.local.entity.FoodTypeEntity
import com.example.nomad.data.local.entity.MainMenuEntity
import com.example.nomad.data.local.entity.ProductEntity
import com.example.nomad.data.use_case.BitmapConverter
import com.example.nomad.data.use_case.DocumentReferenceConverter

@Database(
    entities = [MainMenuEntity::class, FoodTypeEntity::class, ProductEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DocumentReferenceConverter::class, BitmapConverter::class)
abstract class MainDataBase : RoomDatabase() {

    abstract fun mainMenuDao(): MainMenuDao
    abstract fun foodTypeDao(): FoodTypeDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: MainDataBase? = null

        fun getDataBase(context: Context): MainDataBase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MainDataBase::class.java,
                    "nomad_db"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}