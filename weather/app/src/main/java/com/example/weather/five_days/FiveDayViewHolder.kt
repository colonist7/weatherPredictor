package com.example.weather.five_days

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import kotlinx.android.synthetic.main.one_day.view.*


class FiveDayViewHolder (
    view: View
) : RecyclerView.ViewHolder(view) {

    private val date = view.findViewById<TextView>(R.id.one_day)
    private val day_icon = view.findViewById<ImageView>(R.id.day_icon)
    private val day_temp = view.findViewById<TextView>(R.id.day_temp)
    private val night_icon = view.findViewById<ImageView>(R.id.night_icon)
    private val night_temp = view.findViewById<TextView>(R.id.night_temp)

    fun setItem(item: ItemFiveDays) {
        date.text = item.date
        day_icon.setImageResource(R.drawable.img12)
        day_temp.text = item.day_icon
        night_icon.setImageResource(R.drawable.img11)
        night_temp.text = item.night_icon
    }
}