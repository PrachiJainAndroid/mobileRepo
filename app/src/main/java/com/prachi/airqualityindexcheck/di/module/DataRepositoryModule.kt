package com.prachi.airqualityindexcheck.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.android.trackmysleepquality.database.AQIDatabaseDao
import com.prachi.airqualityindexcheck.repository.local.AppSecureSharedPreferences
import com.prachi.airqualityindexcheck.database.AQIDatabase
import com.prachi.airqualityindexcheck.repository.remote.AqiRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import java.lang.ref.WeakReference


val dataRepoModule = module {

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
    single { provideAqiRepository(get(), get()) }


}

fun provideDatabase(application: Application): AQIDatabase {
    return AQIDatabase.getInstance(application)
}

fun provideAQIDao(database: AQIDatabase): AQIDatabaseDao {
    return database.aqiDatabaseDao
}

fun provideAqiRepository(
    sharedPreferences: AppSecureSharedPreferences,
    aqiDatabaseDao: AQIDatabaseDao
) = AqiRepository(sharedPreferences, aqiDatabaseDao)


