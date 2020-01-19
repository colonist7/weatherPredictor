package com.example.weather.five_days

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R

class FiveDayAdapter (
    private val ItemList: List<ItemFiveDays>
) : RecyclerView.Adapter<FiveDayViewHolder>() {

    override fun getItemCount(): Int {
        return ItemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiveDayViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.one_day , parent, false)
        return FiveDayViewHolder(view)
    }

    override fun onBindViewHolder(holder: FiveDayViewHolder, position: Int) {
        holder.setItem(ItemList[position])
    }

}