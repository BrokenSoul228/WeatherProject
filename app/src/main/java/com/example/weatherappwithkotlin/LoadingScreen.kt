package com.example.weatherappwithkotlin

import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
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
        showDialog(context)
        if ((downSpeed != null) && (downSpeed > 80)) {
            Handler(Looper.getMainLooper()).postDelayed({
                showButton()
//                Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
            }, 2800)
        }
        else if(downSpeed in 50..79) {
            Handler(Looper.getMainLooper()).postDelayed({
                showButton()
//                Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
            }, 3000)
        }
        else if(downSpeed in 20..49) {
            Handler(Looper.getMainLooper()).postDelayed({
                showButton()
//                Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
            }, 3200)}
        else if(downSpeed in 10..19) {
            Handler(Looper.getMainLooper()).postDelayed({
                showButton()
//                Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
            }, 4000)
        }
        else if(downSpeed in 3..9) {
            Handler(Looper.getMainLooper()).postDelayed({
                showButton()
//                Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
            }, 4500)
        }
        else {
            Handler(Looper.getMainLooper()).postDelayed({
                showButton()
                warningText.text = "Can't refresh data, check your internet connection"
                Toast.makeText(context, "So slow internet", Toast.LENGTH_SHORT).show()
            }, 7000)
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

    private fun dismissDialog() {
        dialog?.dismiss()
        dialog = null
    }
}