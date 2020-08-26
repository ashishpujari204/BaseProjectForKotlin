package com.ashish.baseproject.ui.baseactivity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ashish.baseproject.BuildConfig
import com.ashish.baseproject.R


abstract class BaseActivity : AppCompatActivity() {
    private lateinit var alertDialogProgressBar: AlertDialog

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }

    open fun showProgressDialog(
    ) {
        val adb = AlertDialog.Builder(this)
        val view: View =
            layoutInflater.inflate(R.layout.custom_progress_bar, null)
        val pb = view.findViewById<View>(R.id.progressBar) as ProgressBar
        adb.setView(view)
        alertDialogProgressBar = adb.create()
        alertDialogProgressBar.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        alertDialogProgressBar.setCancelable(false)
        alertDialogProgressBar.show()
    }

    open fun stopProgressDialog() {
        if (alertDialogProgressBar == null) {
            return
        }
        alertDialogProgressBar.hide()
    }


    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showAlertDialog(dialogBuilder: AlertDialog.Builder.() -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.dialogBuilder()
        val dialog = builder.create()
        dialog.show()
    }

    fun AlertDialog.Builder.positiveButton(
        text: String = "Okay",
        handleClick: (which: Int) -> Unit = {}
    ) {
        this.setPositiveButton(text) { _, which -> handleClick(which) }
    }

    fun AlertDialog.Builder.negativeButton(
        text: String = "Cancel",
        handleClick: (which: Int) -> Unit = {}
    ) {
        this.setNegativeButton(text) { _, which -> handleClick(which) }
    }

    fun View.closeKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    fun logError(message: String) {
        if (BuildConfig.DEBUG)
            Log.e(this.localClassName, message)
    }

    fun logDebug(message: String) {
        if (BuildConfig.DEBUG)
            Log.d(this.localClassName, message)
    }

    fun logInfo(message: String) {
        if (BuildConfig.DEBUG)
            Log.i(this.localClassName, message)
    }
}