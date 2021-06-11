package com.prachi.airqualityindexcheck.activity.home

import com.prachi.airqualityindexcheck.database.AQIModel
import com.prachi.airqualityindexcheck.dialogfragment.FrequencyDialogFragment
import com.prachi.chatmessenger.base.BaseNavigator

interface MainNavigator :BaseNavigator {
    fun askFrequncyofUpdateDialog(frequencyDialogFragment: FrequencyDialogFragment, function: (String?) -> Unit)
    fun setCityListAdapter(cityListAdapter: CityListAdapter, onCitySelect: (AQIModel) -> Unit)


}