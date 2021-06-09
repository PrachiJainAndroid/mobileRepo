package com.prachi.airqualityindexcheck.dialogfragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.prachi.airqualityindexcheck.R
import com.prachi.airqualityindexcheck.databinding.FrequencyDialogFragmentLayoutBinding
import kotlinx.android.synthetic.main.frequency_dialog_fragment_layout.view.*
import java.util.*

class FrequencyDialogFragment  : BottomSheetDialogFragment() {
    private lateinit var mBinding: FrequencyDialogFragmentLayoutBinding
    var onFrequencySelect: ((frequency: String?) -> Unit)? = null
    val freqList = arrayListOf<String>("4 hours", "5 hours")

    var frequency: String? = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NO_FRAME, R.style.FollowUpBottomSheetDialog)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.frequency_dialog_fragment_layout,
            container, false
        )
        val myView: View = mBinding.root
        return myView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.root.btn_setup.setEnabled(false)
        val adapter = ArrayAdapter(activity?.applicationContext!!, android.R.layout.simple_list_item_1, freqList)

        mBinding.root.frequency_list_view.adapter =adapter

        mBinding.root.frequency_list_view.setOnItemClickListener(object :AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                frequency = freqList.get(position)
                mBinding.root.btn_setup.setEnabled(true)
            }

        })






        mBinding.btnSetup.setOnClickListener {
            Log.d("clicked", "onFrequencySelect")
            dismiss()
            onFrequencySelect?.invoke(frequency)
        }

    }







}