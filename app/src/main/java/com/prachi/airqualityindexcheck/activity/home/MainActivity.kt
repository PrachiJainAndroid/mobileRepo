package com.prachi.airqualityindexcheck.activity.home

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import com.prachi.airqualityindexcheck.BR
import com.prachi.airqualityindexcheck.R
import com.prachi.airqualityindexcheck.database.AQIModel
import com.prachi.airqualityindexcheck.databinding.ActivityMainBinding
import com.prachi.airqualityindexcheck.dialogfragment.FrequencyDialogFragment
import com.prachi.chatmessenger.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : BaseActivity<ActivityMainBinding, MainNavigator, MainActivityViewModel>(),
    MainNavigator {

    private var askfrequencyDialogFragment = FrequencyDialogFragment()


    override fun getViewModelClass(): Class<MainActivityViewModel> {
        return MainActivityViewModel::class.java
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //if application is launched first time, ask for frequncy of update of AQI
        mViewModel?.apply {
            appOpenedforFirstTime()
            //set the parameter as false in sharedpreferences

        }



        mViewModel?.allCityList?.observe(this, {
            Log.d("ALL Cities in DB are", it.toString())

            var time: Date? = it?.get(0)?.time
            time?.let {
                val niceDateStr = DateUtils.getRelativeTimeSpanString(
                    time?.getTime()!!,
                    Calendar.getInstance().timeInMillis,
                    DateUtils.MINUTE_IN_MILLIS
                )
                tv_lastUpdatedDate.setText(niceDateStr)
            }

            mViewModel?.setCityWiseAQIListAdapter(it)
        })


    }


    override fun setCityListAdapter(
        cityListAdapter: CityListAdapter,
        onCitySelect: (AQIModel) -> Unit
    ) {
        cityListAdapter.oncitySelect = onCitySelect
        rv_allcitiesAQI.adapter = cityListAdapter
    }


    override fun askFrequncyofUpdateDialog(
        frequencyDialogFragment: FrequencyDialogFragment,
        onFrequencySelect: (String?) -> Unit
    ) {
        askfrequencyDialogFragment = frequencyDialogFragment
        askfrequencyDialogFragment.onFrequencySelect = onFrequencySelect
        askfrequencyDialogFragment.show(supportFragmentManager, "")


    }


}