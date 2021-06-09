package com.prachi.chatmessenger.base

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.prachi.chatmessenger.utils.isNetworkAvailable
import org.koin.androidx.viewmodel.ext.android.getViewModel

import retrofit2.Response
import com.prachi.chatmessenger.utils.guardRun as guardRun1


abstract class BaseActivity<B : ViewDataBinding, N : BaseNavigator, V : BaseViewModel<N>> :
    AppCompatActivity() , BaseViewImpl<B, N, V>,BaseNavigator{

    protected var mViewModel: V? = null
    protected var viewDataBinding: B? = null
    protected abstract fun getViewModelClass(): Class<V>

    protected abstract fun getBindingVariable(): Int



    abstract fun getLayoutID():Int

    @Suppress("DEPRECATION")
    override fun getViewModel(): V? {
        return this.mViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewDataBindingObj = DataBindingUtil.inflate<B>(
            LayoutInflater.from(this@BaseActivity), getLayoutID(), null, false
        )
        this.mViewModel = getViewModel<V>(null,getViewModelClass().kotlin)
        viewDataBindingObj.setVariable(getBindingVariable(), mViewModel)
        setContentView(viewDataBindingObj.root)
        guardRun1 {
            @Suppress("UNCHECKED_CAST")
            getViewModel()?.setNavigator(this as N?)
        }

        //FirebaseAuth.getInstance().createUserWithEmailAndPassword()

    }

    /**
     * Check if Internet is Available or not. Requires ACCESS_NETWORK_STATE Permission.
     */
     override fun isNetworkAvailable() = (this as AppCompatActivity).isNetworkAvailable()


    /**
     * Open Activity as Given By Class With Extras and need toDate be implement
     *
     * @param T type of Activity Class We are about toDate be Open
     * @param cls Activity Class We are about toDate be Open
     * @param extras Optional extras Which will be moved as a Bundle.
     */
     override fun <T> openActivity(cls: Class<T>, extras: Bundle?) {
        Intent(this, cls).apply {
            if (extras != null)
                putExtras(extras)
            startActivity(this)
        }
    }

    override fun registerReceiver(
        broadcastReceiver: BroadcastReceiver,
        intentFilter: IntentFilter,
        useLocalBroadCastManager: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun finishActivity() {
        finish()
    }

    override fun onError(error: String, t: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun onApiFailed(response: Response<*>?, t: Throwable) {
        TODO("Not yet implemented")
    }

    override fun closeKeyBoard() {
        TODO("Not yet implemented")
    }

    override fun showToast(msg: String) {
        TODO("Not yet implemented")
    }

    override fun showHardToast(msg: String) {
        TODO("Not yet implemented")
    }

    override fun showSnackBar(msg: String) {
        TODO("Not yet implemented")
    }

    override fun unRegisterReceiver(
        broadcastReceiver: BroadcastReceiver,
        useLocalBroadCastManager: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun getAppResources(): Resources? {
        TODO("Not yet implemented")
    }

    override fun getAppAssets(): AssetManager? {
        TODO("Not yet implemented")
    }

    override fun showConfirmationDialog(
        msg: String,
        onResponse: (result: Boolean) -> Unit,
        positiveText: String,
        negativedText: String,
        cancelable: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun requestPermission(permission: String, callback: (Boolean) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun hideKeyboard() {
        TODO("Not yet implemented")
    }

    override fun performDataBinding() {
        FrameLayout(this).apply {
            @Suppress("DEPRECATION")
            viewDataBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(this@BaseActivity),
                    getLayoutID(),
                    this,
                    false
                )
            addView(getViewDataBindings()?.root)
            setContentView(this)
    }}

    override fun getViewDataBindings(): B? {
        return this.viewDataBinding
    }
}