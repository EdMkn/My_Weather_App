package com.myprojects.my_weather_app.ui.adapter

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myprojects.my_weather_app.R
import com.myprojects.my_weather_app.data.model.ForecastItem
import java.util.Locale

class ForecastAdapter(private val forecastItems: List<ForecastItem>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
        val tvTemp: TextView = itemView.findViewById(R.id.tvTemp)
        val tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = forecastItems[position]

        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .parse(item.dt_txt)
        val dateStr = SimpleDateFormat("EEE HH:mm", Locale.getDefault()).format(date!!)

        holder.tvDate.text = dateStr
        holder.tvTemp.text = "${item.main.temp.toInt()}°C"
        holder.tvDesc.text = item.weather[0].description

        val iconUrl = "https://openweathermap.org/img/wn/${item.weather[0].icon}@2x.png"
        Glide.with(holder.itemView.context)
            .load(iconUrl)
            .into(holder.ivIcon)
    }

    override fun getItemCount() = forecastItems.size
}