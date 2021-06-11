package com.prachi.airqualityindexcheck.activity.cityaqi

import android.os.Bundle
import com.prachi.airqualityindexcheck.database.AQIModel
import com.prachi.chatmessenger.base.BaseNavigator

interface CityNavigator :BaseNavigator {
    fun getBundle(): Bundle?
    fun setCityData(cityAQIItem: AQIModel?)
    fun setCityListAdapter(cityAQIHistoryListAdapter: CityAQIHistoryListAdapter, onRowClick: (AQIModel) -> Unit)


}