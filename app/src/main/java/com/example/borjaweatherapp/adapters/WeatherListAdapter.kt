package com.example.borjaweatherapp.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.borjaweatherapp.R
import com.example.borjaweatherapp.databinding.WeatherListItemBinding
import com.example.borjaweatherapp.models.CurrentWeather
import com.example.borjaweatherapp.utils.CommonHelper
import java.util.ArrayList

class WeatherListAdapter(
    private val mActivity: Activity,
    private val weatherList: ArrayList<CurrentWeather?>?
) : RecyclerView.Adapter<WeatherListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: WeatherListItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.weather_list_item, parent, false
            )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (weatherList != null) {
            return weatherList.size
        }
        return 0
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val weatherObject = weatherList?.get(position)
        if (weatherObject != null) {
            val currentWeather = weatherObject.weather[0].main
            if (currentWeather == "Rain") {
                holder.mItemBinding
                    .imgIndicator
                    .setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_raining))
            } else {
                val currentTime =
                    CommonHelper.utcTimeStampToLocal(weatherObject.dt, weatherObject.timezone)
                if (CommonHelper.stringToTime(currentTime)) {
                    holder.mItemBinding
                        .imgIndicator
                        .setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_moon))
                } else {
                    holder.mItemBinding
                        .imgIndicator
                        .setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_sun))
                }
            }

            val cityValue = weatherObject.name
            holder.mItemBinding.txtCityCountry.text = "$cityValue, "

            val textValue = holder.mItemBinding.txtCityCountry.text.toString()
            holder.mItemBinding.txtCityCountry.text = textValue + weatherObject.sys.country

            val tempValue = CommonHelper.kelvinToCelsius(weatherObject.main.temp)
            holder.mItemBinding.txtTemp.text = "$tempValue °C"
            holder.mItemBinding.txtSunriseValue.text =
                CommonHelper.utcTimeStampToLocal(weatherObject.sys.sunrise, weatherObject.timezone)
            holder.mItemBinding.txtSunsetValue.text =
                CommonHelper.utcTimeStampToLocal(weatherObject.sys.sunset, weatherObject.timezone)
        }
    }

    inner class ViewHolder(
        itemBinding: WeatherListItemBinding
    ): RecyclerView.ViewHolder(itemBinding.root) {
        val mItemBinding: WeatherListItemBinding = itemBinding
    }
}