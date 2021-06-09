package com.prachi.chatmessenger.base

import android.app.Application
import com.prachi.airqualityindexcheck.di.module.dataRepoModule
import com.prachi.chatmessenger.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
           modules(listOf(viewModelModule,dataRepoModule))
        }
    }
}