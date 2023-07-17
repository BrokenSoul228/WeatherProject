package com.example.weatherappwithkotlin

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class CheckNetworkConnection(private val connectivityManager: ConnectivityManager)  {
        fun isInternetAvailable(): Boolean {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }
}