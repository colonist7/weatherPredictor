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
        when(item.day_icon.toLowerCase()) {
            "clouds" -> day_icon.setImageResource(R.drawable.clouds)
            "clear" -> day_icon.setImageResource(R.drawable.clear)
            "rain" -> day_icon.setImageResource(R.drawable.img4)
            "drizzle" -> day_icon.setImageResource(R.drawable.img5)
            "thunderstorm" -> day_icon.setImageResource(R.drawable.img6)
        }
        when(item.night_icon.toLowerCase()) {
            "clouds" -> night_icon.setImageResource(R.drawable.clouds_night)
            "clear" -> night_icon.setImageResource(R.drawable.clear_night)
            "rain" -> night_icon.setImageResource(R.drawable.img4)
            "drizzle" -> night_icon.setImageResource(R.drawable.img13)
            "thunderstorm" -> day_icon.setImageResource(R.drawable.img6)
        }
        day_temp.text = item.day_temp
        night_temp.text = item.night_temp
    }
}