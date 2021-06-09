package com.prachi.airqualityindexcheck.activity

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android.trackmysleepquality.database.AQIDatabaseDao
import com.prachi.airqualityindexcheck.base.repo.local.preferences.AppSecureSharedPreferences
import com.prachi.airqualityindexcheck.dialogfragment.FrequencyDialogFragment
import com.prachi.airqualityindexcheck.database.AQIModel
import com.prachi.chatmessenger.base.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import java.util.*


class MainActivityViewModel(
    private val sharedPreferences: AppSecureSharedPreferences,
    val dataBase: AQIDatabaseDao
) :
    BaseViewModel<MainNavigator>() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val allRecordsLiveData: LiveData<AQIModel?> = dataBase.get()
    val allCityList = dataBase.getAllValues()
    val channel = BroadcastChannel<List<AQIModel>>(1)

    init {
        uiScope.launch {
        channel.asFlow()
            .debounce(5000)
            .collect {
                saveRecords(it)}
        }
    }


    fun appOpenedforFirstTime() {
        getNavigator()?.askFrequncyofUpdateDialog(FrequencyDialogFragment()) { frequency: String? ->

            frequency
            frequency?.let {
                sharedPreferences.putString("Frequency", frequency)
                    ?.subscribe()
            }
            var myval = sharedPreferences.getString("Frequency", "DefaultValue")
                ?.executeSilent({
                    Log.d("FrequencySavedIs", it)
                }, {})


        }
    }


    fun saveRecordstoDB(aqiModelList: List<AQIModel>) {
       channel.offer(aqiModelList)
    }


    private suspend fun saveRecords(aqiModelList: List<AQIModel>) {
        withContext(Dispatchers.IO) {

            launch {


                for (model in aqiModelList) {
                    var entity = model
                    entity.time = Calendar.getInstance().time

                    // dataBase.insert(entity)
                }
                dataBase.insertList(aqiModelList)


            }


        }
    }

    fun setCityWiseAQIListAdapter() {
        getNavigator()?.setCityListAdapter(CityListAdapter(allCityList.value)) {

        }
    }

    override fun onCleared() {
        super.onCleared()
        channel.close()
    }


}