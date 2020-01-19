package com.example.weather.ITemToday

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R

class ItemTodayAdapter (
    private val ItemList: List<ItemToday>
) : RecyclerView.Adapter<ItemTodayViewHolder>() {

    override fun getItemCount(): Int {
        return ItemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTodayViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.today_fragment_layout , parent, false)
        return ItemTodayViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemTodayViewHolder, position: Int) {
        holder.setItem(ItemList[position])
    }

}