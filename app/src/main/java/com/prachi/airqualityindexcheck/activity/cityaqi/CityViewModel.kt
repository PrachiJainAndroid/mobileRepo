package com.prachi.airqualityindexcheck.activity.cityaqi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.android.trackmysleepquality.database.AQIDatabaseDao
import com.prachi.airqualityindexcheck.repository.local.AppSecureSharedPreferences
import com.prachi.airqualityindexcheck.database.AQIModel
import com.prachi.airqualityindexcheck.repository.remote.AqiRepository
import com.prachi.chatmessenger.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

@FlowPreview
@ExperimentalCoroutinesApi
class CityViewModel(
    private val sharedPreferences: AppSecureSharedPreferences,
    val dataBase: AQIDatabaseDao,
    val aqiRepository: AqiRepository
) : BaseViewModel<CityNavigator>() {


    var cityAQIItem: AQIModel? = null
    private val allCityList: LiveData<List<AQIModel>?> = aqiRepository.getAqiSubscription()
    var cityDataForAllIntervals = MutableLiveData<ArrayList<AQIModel>?>(ArrayList())
    private var subscriptionObserver = Observer<List<AQIModel>?> { aqiList ->
        val updatedCityData = aqiList?.first { it.city == cityAQIItem?.city }
        updatedCityData?.also {
            val list = cityDataForAllIntervals.value
            list?.add(it)
            cityDataForAllIntervals.value = list
        }
    }

    @ExperimentalTime
    fun start() {
        val bundle = getNavigator()?.getBundle()
        cityAQIItem = bundle?.getParcelable<AQIModel>("AQIModel")
        getNavigator()?.setCityData(cityAQIItem)
        allCityList.observeForever(subscriptionObserver)
        viewModelScope.launch(Dispatchers.IO) {
            aqiRepository.getAqiDataForCity()
        }

    }

    fun setCityAQIHistoryListAdapter(cityAQIHistoryList: List<AQIModel>?) {
        getNavigator()?.setCityListAdapter(CityAQIHistoryListAdapter(cityAQIHistoryList)) {

        }
    }

    override fun onCleared() {
        super.onCleared()
        allCityList.removeObserver(subscriptionObserver)
    }

}