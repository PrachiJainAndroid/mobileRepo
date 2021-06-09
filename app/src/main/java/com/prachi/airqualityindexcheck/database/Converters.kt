package com.prachi.airqualityindexcheck.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

class Converters {

    @TypeConverter
    fun listToJson(value: List<AQIModel>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<AQIModel>::class.java).toList()

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    @TypeConverter
    fun toTimestamp(value: Date?): Long? {
        return value?.let { value.time }
    }

}