package com.example.weatherappwithkotlin

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherappwithkotlin.screen.MainScreen
import com.example.weatherappwithkotlin.screen.fragment.NoInternetConnection

class MainActivity : AppCompatActivity() {

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkConnection : CheckNetworkConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkConnection = CheckNetworkConnection(connectivityManager)
        switchScreenNetwork()
    }

    private fun switchScreenNetwork() {
        val fragment = if (networkConnection.isInternetAvailable()) MainScreen() else NoInternetConnection()
        supportFragmentManager.beginTransaction()
            .replace(R.id.MainScreenSwitcher, fragment)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onStop() {
        super.onStop()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
        }

        override fun onAvailable(network: Network) {
        }
    }
}