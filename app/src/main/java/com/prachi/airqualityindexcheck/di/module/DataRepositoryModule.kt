package com.prachi.airqualityindexcheck.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.android.trackmysleepquality.database.AQIDatabaseDao
import com.prachi.airqualityindexcheck.base.repo.local.preferences.AppSecureSharedPreferences
import com.prachi.airqualityindexcheck.database.AQIDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import java.lang.ref.WeakReference



val dataRepoModule =module {

    single<SharedPreferences> {
        androidApplication().getSharedPreferences(
            androidApplication().packageName.replace(".", "_") + "_basePreferences",
            Context.MODE_PRIVATE
        )
    }


    single {
        AppSecureSharedPreferences(WeakReference(get()))
    }
    single { provideDatabase(androidApplication()) }
    single { provideAQIDao(get()) }





}

fun provideDatabase(application: Application): AQIDatabase {
    return AQIDatabase.getInstance(application)
    /* Room.databaseBuilder(application, AQIDatabase::class.java, "countries")
         .fallbackToDestructiveMigration()
         .build()*/
}

fun provideAQIDao(database: AQIDatabase): AQIDatabaseDao {
    return  database.aqiDatabaseDao
}


