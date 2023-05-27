package com.example.borjaweatherapp.ui.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.borjaweatherapp.R
import com.example.borjaweatherapp.adapters.WeatherListAdapter
import com.example.borjaweatherapp.databinding.FragmentWeatherListBinding
import com.example.borjaweatherapp.managers.SharedPreferenceManager
import com.example.borjaweatherapp.ui.customdialog.BasicDialog
import com.example.borjaweatherapp.viewmodel.WeatherListViewModel
import com.example.borjaweatherapp.viewmodelfactory.WeatherListFactory
import com.example.borjaweatherapp.ui.customdialog.ProgressDialog.Companion.hideProgressDialog
import com.example.borjaweatherapp.ui.customdialog.ProgressDialog.Companion.showProgressDialog

class WeatherListFragment : Fragment() {

    private lateinit var mContext: FragmentActivity
    private lateinit var weatherListViewModel: WeatherListViewModel
    private var _binding: FragmentWeatherListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as FragmentActivity
    }

    override fun onResume() {
        super.onResume()
        binding.toolbar.txtToolbarTitle.text = mContext.getText(R.string.weather_history_title)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext.viewModelStore.clear()
        weatherListViewModel =
            ViewModelProvider(
                mContext,
                WeatherListFactory(mContext.application, mContext)
            ).get(WeatherListViewModel::class.java)

        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.toolbar.imgBack.isVisible = false
        binding.toolbar.imgLogout.setOnClickListener {
            BasicDialog().alertPopup(
                mContext.supportFragmentManager,
                mContext.getString(R.string.information),
                mContext.getString(R.string.logout_message),
                false,
                mContext.getString(R.string.weather_list)
            )
        }

        weatherListViewModel.showLoader().observe(mContext, Observer {
            if (it == true) {
                context?.let { mContext -> showProgressDialog(mContext) }
            } else {
                hideProgressDialog()
            }
        })

        val weatherList =
            SharedPreferenceManager.getArrayList(mContext, mContext.getString(R.string.data_list))

        binding.rvWeatherList.setHasFixedSize(false)
        binding.rvWeatherList.layoutManager = LinearLayoutManager(mContext)
        binding.rvWeatherList.adapter = WeatherListAdapter(mContext, weatherList)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}