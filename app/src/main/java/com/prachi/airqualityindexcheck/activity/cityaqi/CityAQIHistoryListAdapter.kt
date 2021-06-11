package com.prachi.airqualityindexcheck.activity.cityaqi



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.prachi.airqualityindexcheck.BR
import com.prachi.airqualityindexcheck.R
import com.prachi.airqualityindexcheck.database.AQIModel
import com.prachi.airqualityindexcheck.databinding.CityItemHistoryRowBinding
import com.prachi.airqualityindexcheck.databinding.CityRowBinding

class CityAQIHistoryListAdapter(var CityItemList: List<AQIModel?>?) :
    RecyclerView.Adapter<CityAQIHistoryListAdapter.MyViewHolder>() {
    lateinit var mContext: Context
    var oncitySelect: ((AQIModel) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        mContext = parent.context
        return MyViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.city_item_history_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return CityItemList?.size!!
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var cityItem = CityItemList?.get(position)

        holder.getBinding()?.setVariable(BR.model, cityItem)
        val inBinding = holder.getBinding() as CityItemHistoryRowBinding

        inBinding.mainlayout.setOnClickListener{
            oncitySelect?.invoke(cityItem!!)
        }
        if(cityItem?.aqi!!<100){
            inBinding.tvAQI.setTextColor(ContextCompat.getColor(mContext,R.color.teal_700))
        }else if(cityItem?.aqi!!>=100 && cityItem?.aqi!!<200){
            inBinding.tvAQI.setTextColor(ContextCompat.getColor(mContext,R.color.orange))
        }else{
            inBinding.tvAQI.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary))
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