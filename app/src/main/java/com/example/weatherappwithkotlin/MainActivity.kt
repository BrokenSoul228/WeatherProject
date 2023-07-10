package com.example.weatherappwithkotlin

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherappwithkotlin.screen.MainScreen

class MainActivity : AppCompatActivity() {

    var pref : SharedPreferences? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = getSharedPreferences("TABLE", Context.MODE_PRIVATE)
        if(isInternetAvailabel() == true){
            val fragment = MainScreen()
            supportFragmentManager.beginTransaction()
                .replace(R.id.MainScreenSwitcher, fragment)
                .commit()
        }
        else{
            val fragment = NoInternet()
            supportFragmentManager.beginTransaction()
                .replace(R.id.MainScreenSwitcher, fragment)
                .commit()
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isInternetAvailabel() : Boolean?{
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun saveData(res : Int){
        val editor = pref?.edit()
        editor?.putInt("counter", res)
        editor?.apply()
    }

}