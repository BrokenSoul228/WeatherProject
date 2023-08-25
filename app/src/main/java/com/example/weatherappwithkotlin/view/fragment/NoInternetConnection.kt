package com.example.weatherappwithkotlin.view.fragment

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.weatherappwithkotlin.R
import com.example.weatherappwithkotlin.view.MainFragment
import com.google.android.material.snackbar.Snackbar

class NoInternetConnection : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_no_internet2, container, false)
        val retryButton: Button = view.findViewById(R.id.RetryButton)
        retryButton.setOnClickListener {
            if (isInternetAvailable()) {
                val fragment = MainFragment()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.remove(this@NoInternetConnection)
                transaction.replace(R.id.nav_container, fragment).commit()
            } else {
                showSnackBarWithSettingsButton()
            }
        }
        return view
    }

    private fun showSnackBarWithSettingsButton() {
        val snackBar = Snackbar.make(
            requireView(),
            "No internet connection!",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Settings") {
            openPhoneSettings()
        }
        snackBar.show()
    }

    private fun openPhoneSettings() {
        val settingsIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
        startActivity(settingsIntent)
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}
