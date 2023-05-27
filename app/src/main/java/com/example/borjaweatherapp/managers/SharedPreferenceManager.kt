package com.example.borjaweatherapp.managers

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.borjaweatherapp.models.CurrentWeather
import com.example.borjaweatherapp.models.User
import com.example.borjaweatherapp.utils.CommonHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.Hashtable
import kotlin.collections.HashMap

class SharedPreferenceManager {

    companion object {
        private val TAG = SharedPreferenceManager::class.java.name
        const val APP_PREFERENCES = "App_prefs"
        private var sharedPreferences: SharedPreferences? = null
        private var hashMap: HashMap<String, String>? = null
        private var hashtable: Hashtable<String, Any>? = null
        private val task = Any()
        const val IMEI = "IMEI"

        fun initialize(context: Context) {
            sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, 0)
            val map: MutableMap<String, *>? = sharedPreferences?.all
            hashMap = map as HashMap<String, String>?
            hashtable = Hashtable<String, Any>()
        }

        fun clearSharedPrefData() {
            try {
                synchronized(task) {
                    val sharedPref: SharedPreferences.Editor? = sharedPreferences?.edit()
                    sharedPref?.clear()
                    sharedPref?.apply()
                    hashMap?.clear()
                    hashtable?.clear()
                }
                Log.d(TAG, " cleared shared pref data")
            } catch (exception: Exception) {
                Log.e(TAG, "clearSharedPrefData() e= $exception")
            }
        }

        fun getValue(key: String?): Any? {
            return if (key == null) null else hashtable?.get(key)
        }

        fun setValue(key: String?, value: Any) {
            try {
                synchronized(task) {
                    hashtable?.put(key, value)
                }
            } catch (exception: Exception) {
                throw exception
            }
        }

        fun deleteValue(key: String?) {
            hashtable?.remove(key)
        }

        fun saveArrayList(activity: Activity, key: String?, list: MutableList<CurrentWeather>) {
            val myPrefs = activity.getSharedPreferences(
                APP_PREFERENCES,
                Activity.MODE_PRIVATE
            )
            val prefsEditor = myPrefs.edit()
            val gson = Gson()
            val json: String = gson.toJson(list)
            prefsEditor.putString(key, CommonHelper.encrypt(json))
            prefsEditor.apply()
        }

        fun getArrayList(activity: Activity, key: String?): java.util.ArrayList<CurrentWeather?>? {
            val myPrefs = activity.getSharedPreferences(
                APP_PREFERENCES,
                Activity.MODE_PRIVATE
            )
            val gson = Gson()
            val json: String? = (myPrefs.getString(key, ""))
            val type: Type = object : TypeToken<java.util.ArrayList<CurrentWeather?>?>() {}.type
            return gson.fromJson(CommonHelper.decrypt(json), type)
        }

        fun saveUserArrayList(activity: Activity, key: String?, list: MutableList<User>) {
            val myPrefs = activity.getSharedPreferences(
                APP_PREFERENCES,
                Activity.MODE_PRIVATE
            )
            val prefsEditor = myPrefs.edit()
            val gson = Gson()
            val json: String = gson.toJson(list)
            prefsEditor.putString(key, CommonHelper.encrypt(json))
            prefsEditor.apply()
        }

        fun getUserArrayList(activity: Activity, key: String?): java.util.ArrayList<User?>? {
            val myPrefs = activity.getSharedPreferences(
                APP_PREFERENCES,
                Activity.MODE_PRIVATE
            )
            val gson = Gson()
            val json: String? = (myPrefs.getString(key, ""))
            val type: Type = object : TypeToken<java.util.ArrayList<User?>?>() {}.type
            return gson.fromJson(CommonHelper.decrypt(json), type)
        }
    }
}