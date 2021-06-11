package com.prachi.chatmessenger.di.module


import com.prachi.airqualityindexcheck.activity.cityaqi.CityViewModel
import com.prachi.airqualityindexcheck.activity.home.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainActivityViewModel(get(),get(),get())

    }
    viewModel { CityViewModel(get(),get(),get()) }
}