package com.prachi.airqualityindexcheck.activity.cityaqi

import android.os.Bundle
import com.prachi.airqualityindexcheck.BR
import com.prachi.airqualityindexcheck.R
import com.prachi.airqualityindexcheck.activity.home.CityListAdapter
import com.prachi.airqualityindexcheck.database.AQIModel
import com.prachi.airqualityindexcheck.databinding.CityActivityBinding
import com.prachi.chatmessenger.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.city_activity.*
import kotlinx.android.synthetic.main.city_activity.tvheading
import kotlin.time.ExperimentalTime

class CityAQIHistoryActivity : BaseActivity<CityActivityBinding, CityNavigator, CityViewModel>(),
    CityNavigator {



    override fun getViewModelClass(): Class<CityViewModel> {
        return CityViewModel::class.java
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutID(): Int {
        return R.layout.city_activity
    }


    override fun getBundle(): Bundle? {
        return intent.extras
    }


    override fun setCityData(cityAQIItem: AQIModel?) {
        tvheading.setText(cityAQIItem?.city)
    }

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel?.start()
        mViewModel?.cityDataForAllIntervals?.observe(this,{

            mViewModel?.setCityAQIHistoryListAdapter(it)
        })
    }

    override fun setCityListAdapter(
        cityAQIHistoryListAdapter: CityAQIHistoryListAdapter,
        onRowClick: (AQIModel) -> Unit
    ) {
        cityAQIHistoryListAdapter.oncitySelect = onRowClick
        rv_cityAQI.adapter = cityAQIHistoryListAdapter
    }

}
