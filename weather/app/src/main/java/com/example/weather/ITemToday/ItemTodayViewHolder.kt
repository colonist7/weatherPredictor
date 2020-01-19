package com.example.weather.ITemToday
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R

class ItemTodayViewHolder (
    view: View
) : RecyclerView.ViewHolder(view) {

    private val temp = view.findViewById<TextView>(R.id.temp)
    private val src = view.findViewById<ImageView>(R.id.icon)
    private val desc = view.findViewById<TextView>(R.id.status)

    fun setItem(item: ItemToday) {
        temp.text = item.temp
        src.setImageResource(R.drawable.img15)
        desc.text = item.desc
    }
}