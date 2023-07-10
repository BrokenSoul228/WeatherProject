package com.example.weatherappwithkotlin

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.weatherappwithkotlin.screen.MainScreen

class NoInternet : Fragment() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_no_internet2, container, false)
        val retryButton : Button = view.findViewById(R.id.RetryButton)
        retryButton.setOnClickListener {
            if (isInternetAvailabel() == true){
            val fragment = MainScreen()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.remove(this@NoInternet)
            transaction.replace(R.id.nav_container, fragment).commit()
            }
        }

        return view
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isInternetAvailabel() : Boolean?{
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
