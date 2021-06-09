package com.prachi.airqualityindexcheck.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.prachi.airqualityindexcheck.BR
import com.prachi.airqualityindexcheck.R
import com.prachi.airqualityindexcheck.database.AQIModel
import com.prachi.airqualityindexcheck.databinding.CityRowBinding

class CityListAdapter(var CityItemList: List<AQIModel?>?) :
    RecyclerView.Adapter<CityListAdapter.MyViewHolder>() {
    lateinit var mContext: Context
    var oncitySelect: ((AQIModel) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        mContext = parent.context
        return MyViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.city_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return CityItemList?.size!!
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var cityItem = CityItemList?.get(position)

        holder.getBinding()?.setVariable(BR.model, cityItem)
        val inBinding:CityRowBinding =
            holder.getBinding() as CityRowBinding

        inBinding.mainlayout.setOnClickListener{
            oncitySelect?.invoke(cityItem!!)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var binding: ViewDataBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }

        fun getBinding(): ViewDataBinding? {
            return binding
        }
    }
}