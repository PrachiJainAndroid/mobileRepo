package com.prachi.airqualityindexcheck.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.prachi.airqualityindexcheck.BR
import com.prachi.airqualityindexcheck.R
import com.prachi.airqualityindexcheck.databinding.ActivityMainBinding
import com.prachi.airqualityindexcheck.dialogfragment.FrequencyDialogFragment
import com.prachi.airqualityindexcheck.database.AQIModel
import com.prachi.chatmessenger.base.BaseActivity
import com.prachi.chatmessenger.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.util.*
import javax.net.ssl.SSLSocketFactory


class MainActivity : BaseActivity<ActivityMainBinding, MainNavigator, MainActivityViewModel>(),
    MainNavigator {

    private var askfrequencyDialogFragment = FrequencyDialogFragment()
    private lateinit var webSocketClient: WebSocketClient



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
        }

        mViewModel?.allRecordsLiveData?.observe(this,androidx.lifecycle.Observer<AQIModel?>{value->

            value?.let {
            Log.d("Value from liveData",value.toString())
            }

        })

        mViewModel?.allCityList?.observe(this,Observer<List<AQIModel?>>{
            Log.d("ALL Cities in DB are",it.toString())
            mViewModel?.setCityWiseAQIListAdapter()
        })


    }


    override fun setCityListAdapter(
        cityListAdapter: CityListAdapter,
        onCitySelect: (AQIModel) -> Unit) {
        cityListAdapter.oncitySelect = onCitySelect
        rv_cityAQI.adapter = cityListAdapter
    }

    override fun onResume() {
        super.onResume()
        initWebSocket()
        val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
        webSocketClient.setSocketFactory(socketFactory)
        webSocketClient.connect()

    }

    override fun onPause() {
        super.onPause()
        webSocketClient.close()
    }

    fun initWebSocket() {
        val aqiBaseUri: URI? = URI(Constants.WEB_SOCKET_URL)
        createWebSocketClient(aqiBaseUri)
    }

    private fun createWebSocketClient(aqiBaseUri: URI?) {
        webSocketClient = object : WebSocketClient(aqiBaseUri) {
            override fun onOpen(handshakedata: ServerHandshake?) {

            }

            override fun onMessage(message: String?) {
               Log.d("Message Received:::", message!!)
                val gson = Gson()
                //save data only after 30 sec

                val aqiModel= gson.fromJson(message, Array<AQIModel>::class.java).asList()

                mViewModel?.saveRecordstoDB(aqiModel)

               //after it is saved in DB, close the socket

            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
               // unsubscribe()
            }

            override fun onError(ex: Exception?) {
                Log.e("ERROR", "onError: ${ex?.message}")

            }

        }
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