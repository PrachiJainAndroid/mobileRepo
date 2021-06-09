package com.prachi.airqualityindexcheck.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.trackmysleepquality.database.AQIDatabaseDao

@Database(entities = [AQIModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AQIDatabase : RoomDatabase() {

    abstract val aqiDatabaseDao: AQIDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: AQIDatabase? = null

        fun getInstance(context: Context): AQIDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                        AQIDatabase::class.java,
                            "AQI_history_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}