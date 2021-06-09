package com.prachi.airqualityindexcheck.database

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "AQI_Table")
@TypeConverters(Converters::class)
@Parcelize
data class AQIModel(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name ="city")
    var city: String ,
    @NonNull
    @ColumnInfo(name ="aqi")
    var aqi: Double? = 0.0,
    @NonNull
    @ColumnInfo(name ="time")
    var time: Date? = Calendar.getInstance().time

):Parcelable


