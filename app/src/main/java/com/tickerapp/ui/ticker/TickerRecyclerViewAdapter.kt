package com.tickerapp.ui.ticker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.robinhood.ticker.TickerUtils
import com.tickerapp.R
import com.tickerapp.databinding.ListItemBinding


/*

Created by steph on 11/23/2021

*/
class TickerRecyclerViewAdapter :
    RecyclerView.Adapter<TickerRecyclerViewAdapter.MyviewHolder>() {
    private var tickerData: List<TickerData>? = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyviewHolder(binding)
    }

    class MyviewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TickerData?) {
         //   binding.tvCoinPrice.setCharacterLists(TickerUtils.provideNumberList())

            if (binding.tvCoinPrice.tag != null) {

                val tempPrice = binding.tvCoinPrice.tag as Float
                if (tempPrice < data?.price?.toFloat()!!)
                    binding.ivArrow.setImageResource(R.drawable.arrow_down)
                else
                    binding.ivArrow.setImageResource(R.drawable.arrow_up)

            }

            binding.tvCoinId.text = "Pair id - " + data?.id
            binding.tvCoinPrice.text = "$" + data?.price
            binding.tvCoinPrice.tag = data?.price?.toFloat()

        }
    }

    fun setData(data: List<TickerData>) {
        tickerData = data
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.bind(tickerData?.get(position))
    }

    override fun getItemCount(): Int {
        return tickerData?.size!!
    }

}