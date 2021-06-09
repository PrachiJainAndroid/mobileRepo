package com.prachi.chatmessenger.di.module


import com.prachi.airqualityindexcheck.activity.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainActivityViewModel(get(),get())
    }
}