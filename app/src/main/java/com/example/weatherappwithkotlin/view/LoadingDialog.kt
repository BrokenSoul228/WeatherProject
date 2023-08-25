package com.example.weatherappwithkotlin.view

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.weatherappwithkotlin.R

class LoadingDialog(private val myActivity: Fragment) {

    private lateinit var closeBTN: Button
    private lateinit var warningText: TextView
    private lateinit var loading: TextView
    private lateinit var doneImage: ImageView
    private lateinit var bar: ProgressBar
    private var dialog: AlertDialog? = null

    @SuppressLint("SetTextI18n")
    fun startLoadingDialog() {
        showDialog()
    }

    private fun showDialog() {
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

    fun dismissDialog() {
        dialog?.dismiss()
        dialog = null
    }
}