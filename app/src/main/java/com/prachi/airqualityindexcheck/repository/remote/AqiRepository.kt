package com.prachi.airqualityindexcheck.repository.remote

import androidx.lifecycle.LiveData
import com.example.android.trackmysleepquality.database.AQIDatabaseDao
import com.google.gson.Gson
import com.prachi.airqualityindexcheck.repository.local.AppSecureSharedPreferences
import com.prachi.airqualityindexcheck.database.AQIModel
import com.prachi.airqualityindexcheck.repository.remote.network.AqiSocket
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

class AqiRepository(
    private val sharedPreferences: AppSecureSharedPreferences,
    private val aqiDatabaseDao: AQIDatabaseDao
) {

    private val gson = Gson()

    @ExperimentalCoroutinesApi
    @FlowPreview
    suspend fun getAqiDataForHome(): LiveData<List<AQIModel>?> {
        val intervalHours = sharedPreferences.getInt("update_interval", 4).blockingGet()
        val lastUpdateTime = sharedPreferences.getLong("last_update_time", 0L).blockingGet()
        val currentTime = Date().time
        val diffMillis = currentTime - lastUpdateTime
        if (diffMillis > TimeUnit.HOURS.toMillis(intervalHours.toLong())) {
            // update db with latest data
            //tells how many records we want
            AqiSocket().start().take(1)
                .map { gson.fromJson(it, Array<AQIModel>::class.java).asList() }.collect {
                    for (model in it) {
                        var entity = model
                        entity.time = Calendar.getInstance().time
                    }

                    aqiDatabaseDao.insertList(it)
                }
        }
        return aqiDatabaseDao.getAllValues()
    }

    @ExperimentalTime
    @ExperimentalCoroutinesApi
    @FlowPreview
    suspend fun getAqiDataForCity() {
        //check later why this isnt working with higher values of debounce
        AqiSocket().start().sample(30000)
            .map { gson.fromJson(it, Array<AQIModel>::class.java).asList() }.collect {
                val time = Calendar.getInstance().time
                it.forEach { model -> if (model.time == null) model.time = time }
                aqiDatabaseDao.insertList(it)
            }
    }

    fun getAqiSubscription() = aqiDatabaseDao.getAllValues()

}