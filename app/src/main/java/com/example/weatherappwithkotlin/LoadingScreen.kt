package com.example.weatherappwithkotlin

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class LoadingScreen(private val myActivity: Fragment) {
    private var dialog: AlertDialog? = null
    private lateinit var closeBTN: Button
    private lateinit var warningText: TextView
    private lateinit var loading: TextView
    private lateinit var doneImage: ImageView
    private lateinit var bar: ProgressBar

    fun startLoadingDialog(context: Context) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // Network Capabilities of Active Network
        val nc = cm.getNetworkCapabilities(cm.activeNetwork)
        // DownSpeed in MBPS
        val downSpeed = (nc?.linkDownstreamBandwidthKbps)?.div(1000)
        // UpSpeed  in MBPS
        val upSpeed = (nc?.linkUpstreamBandwidthKbps)
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        val downloadSpeed = networkCapabilities?.linkDownstreamBandwidthKbps?.div(1000) // Convert speed from kbps to Mbps
        showDialog(context)
        if ((upSpeed != null) && (upSpeed in 15000..49000)) {
            Handler().postDelayed({
                showButton()
                Toast.makeText(context, "4G-", Toast.LENGTH_SHORT).show()
            }, 3000) // Show button after 3 seconds for fast speeds
        }
        else if(upSpeed in 50000..80000) {
            Handler().postDelayed({
                showButton()
                Toast.makeText(context, "LTE + 5g", Toast.LENGTH_SHORT).show()
            }, 2800)
        }
        else if(upSpeed in 10000..14000) {
            Handler().postDelayed({
                showButton()
                Toast.makeText(context, "$upSpeed", Toast.LENGTH_SHORT).show()
            }, 3200)}
        else if(upSpeed in 5000..9000) {
            Handler().postDelayed({
                showButton()
                Toast.makeText(context, "$upSpeed +1", Toast.LENGTH_SHORT).show()
            }, 6000) // Show button after 5 seconds for slow speeds
        }
        else {
            Handler().postDelayed({
                showButton()
                Toast.makeText(context, "+1", Toast.LENGTH_SHORT).show()
            }, 8000)
        }
    }

    private fun showDialog(context: Context) {
        if (myActivity.isAdded && !myActivity.isRemoving && !myActivity.isDetached) {
            val builder = AlertDialog.Builder(myActivity.requireContext())
            val inflater = myActivity.layoutInflater
            builder.setView(inflater.inflate(R.layout.custom_loading_screen, null))
            builder.setCancelable(false)
            dialog = builder.create()
            dialog?.show()

            closeBTN = dialog?.findViewById(R.id.button)!!
            warningText = dialog?.findViewById(R.id.warningText)!!
            doneImage = dialog?.findViewById(R.id.imageDone)!!
            bar = dialog?.findViewById(R.id.progressBar)!!
            loading = dialog?.findViewById(R.id.textView2)!!
            warningText.visibility = View.INVISIBLE
            closeBTN.visibility = View.INVISIBLE
            doneImage.visibility = View.INVISIBLE
        }
        closeBTN.setOnClickListener {
            dismissDialog()
        }
    }

    private fun showButton() {
        if (closeBTN.visibility != View.VISIBLE) {
            closeBTN.visibility = View.VISIBLE
            warningText.visibility = View.VISIBLE
            doneImage.visibility = View.VISIBLE
            loading.text = "Done!"
            bar.visibility = View.INVISIBLE
        }
    }

    fun dismissDialog() {
        dialog?.dismiss()
        dialog = null
    }
}