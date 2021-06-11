package com.prachi.airqualityindexcheck.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.prachi.airqualityindexcheck.database.AQIModel
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class BindingAdapter  {

    companion object{

        @BindingAdapter("formattedAQI")
        @JvmStatic
        fun formattedAQI(tv: TextView, aqiModel: AQIModel) {
            val dfComma = DecimalFormat("##,##,##0.00")

            tv.text = String.format(
                dfComma.format(aqiModel.aqi)
            )

        }

        @BindingAdapter("formattedtime")
        @JvmStatic
        fun formattedtime(tv: TextView, aqiModel: AQIModel){
            val outputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
            val outputString: String = outputFormat.format(aqiModel.time)
            tv.text = outputString
        }
    }
}