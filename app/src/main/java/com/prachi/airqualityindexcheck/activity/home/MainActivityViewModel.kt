package com.prachi.airqualityindexcheck.activity.home

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android.trackmysleepquality.database.AQIDatabaseDao
import com.prachi.airqualityindexcheck.activity.cityaqi.CityAQIHistoryActivity
import com.prachi.airqualityindexcheck.repository.local.AppSecureSharedPreferences
import com.prachi.airqualityindexcheck.dialogfragment.FrequencyDialogFragment
import com.prachi.airqualityindexcheck.database.AQIModel
import com.prachi.airqualityindexcheck.repository.remote.AqiRepository
import com.prachi.chatmessenger.base.BaseViewModel
import kotlinx.coroutines.*


@FlowPreview
@ExperimentalCoroutinesApi
class MainActivityViewModel(
    private val sharedPreferences: AppSecureSharedPreferences,
    val dataBase: AQIDatabaseDao,
    val aqiRepository: AqiRepository
) :
    BaseViewModel<MainNavigator>() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var allCityList: LiveData<List<AQIModel>?> = dataBase.getAllValues()

    init {

        //binding it to lifecycle of the viewmodel
        viewModelScope.launch(Dispatchers.IO) {
            allCityList = aqiRepository.getAqiDataForHome()
        }
    }

    fun setCityWiseAQIListAdapter(aqiList: List<AQIModel>?) {
        getNavigator()?.setCityListAdapter(CityListAdapter(aqiList)) {

            val bundle = Bundle()
            bundle.putParcelable("AQIModel", it)
            getNavigator()?.openActivity(CityAQIHistoryActivity::class.java, bundle)

        }
    }

    fun appOpenedforFirstTime() {
        getNavigator()?.askFrequncyofUpdateDialog(FrequencyDialogFragment()) { frequency: String? ->
            frequency?.let {
                sharedPreferences.putString("Frequency", frequency).subscribe()
                sharedPreferences.putInt("update_interval", it[0].toInt()).subscribe()
            }
            var myval = sharedPreferences.getString("Frequency", "DefaultValue")
                ?.executeSilent({
                    Log.d("FrequencySavedIs", it)
                }, {})


        }
    }





}