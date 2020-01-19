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
        when(item.src.toLowerCase()) {
            "clouds" -> src.setImageResource(R.drawable.clouds)
            "clear" -> src.setImageResource(R.drawable.clear)
            "rain" -> src.setImageResource(R.drawable.img4)
            "drizzle" -> src.setImageResource(R.drawable.img5)
            "thunderstorm" -> src.setImageResource(R.drawable.img6)
        }
        temp.text = item.temp
        desc.text = item.desc
    }
}