package com.example.borjaweatherapp.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.borjaweatherapp.BaseScreen
import com.example.borjaweatherapp.R
import com.example.borjaweatherapp.databinding.FragmentCurrentWeatherBinding
import com.example.borjaweatherapp.ui.customdialog.BasicDialog
import com.example.borjaweatherapp.utils.CommonHelper
import com.example.borjaweatherapp.viewmodel.CurrentWeatherViewModel
import com.example.borjaweatherapp.viewmodelfactory.CurrentWeatherFactory
import com.example.borjaweatherapp.ui.customdialog.ProgressDialog

class CurrentWeatherFragment : Fragment() {

    private lateinit var mContext: FragmentActivity
    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel
    private var _binding: FragmentCurrentWeatherBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as FragmentActivity
    }

    override fun onResume() {
        super.onResume()
        binding.toolbar.txtToolbarTitle.text = mContext.getText(R.string.current_weather_title)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mContext.viewModelStore.clear()
        currentWeatherViewModel =
            ViewModelProvider(
                mContext,
                CurrentWeatherFactory(mContext.application, mContext)
            ).get(CurrentWeatherViewModel::class.java)

        _binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.toolbar.imgBack.isVisible = false
        binding.toolbar.imgLogout.setOnClickListener {
            BasicDialog().alertPopup(
                (mContext as BaseScreen).supportFragmentManager,
                (mContext as BaseScreen).getString(R.string.information),
                (mContext as BaseScreen).getString(R.string.logout_message),
                twoButton = true,
                (mContext as BaseScreen).getString(R.string.current_weather)
            )
        }

        currentWeatherViewModel.showLoader().observe(mContext, {
            if (it == true) {
                context?.let { mContext -> ProgressDialog.showProgressDialog(mContext) }
            } else {
                ProgressDialog.hideProgressDialog()
            }
        })

        // show error dialog
        currentWeatherViewModel.getErrorMessage().observe(mContext, {
            Toast.makeText(
                mContext,
                it,
                Toast.LENGTH_SHORT
            ).show()
        })

        currentWeatherViewModel.getTimeFetched().observe(mContext, {

        })

        currentWeatherViewModel.getCurrentWeather().observe(mContext, {
            if (it == "Rain") {
                binding.imgIndicator.setImageDrawable(
                    ContextCompat.getDrawable(mContext, R.drawable.ic_raining))
            } else {
                currentWeatherViewModel.getTimeFetched().value?.let { value ->
                    if (CommonHelper.stringToTime(value)) {
                        binding.imgIndicator.setImageDrawable(
                            ContextCompat.getDrawable(mContext, R.drawable.ic_moon))
                    } else {
                        binding.imgIndicator.setImageDrawable(
                            ContextCompat.getDrawable(mContext, R.drawable.ic_sun))
                    }
                }
            }
        })

        currentWeatherViewModel.getCity().observe(mContext, {
            binding.txtCityCountry.text = "$it, "
            binding.llContainer.visibility = View.VISIBLE
        })

        currentWeatherViewModel.getCountry().observe(mContext, {
            val textValue = binding.txtCityCountry.text.toString()
            binding.txtCityCountry.text = textValue + it
        })

        currentWeatherViewModel.getTemperature().observe(mContext, {
            binding.txtTemp.text = "$it °C"
        })

        currentWeatherViewModel.getSunrise().observe(mContext, {
            binding.txtSunriseValue.text = it.toString()
        })

        currentWeatherViewModel.getSunset().observe(mContext, {
            binding.txtSunsetValue.text = it.toString()
        })

        binding.fabProceed.setOnClickListener {
            currentWeatherViewModel.validateInputData(mContext, binding.tieSearch.text.toString().trim())

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}