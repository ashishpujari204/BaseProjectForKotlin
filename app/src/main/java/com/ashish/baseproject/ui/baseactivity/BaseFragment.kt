package com.ashish.baseproject.ui.baseactivity

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ashish.baseproject.BuildConfig
import com.ashish.baseproject.R

abstract class BaseFragment : Fragment() {

    private lateinit var alertDialogProgressBar: AlertDialog
    private var parentActivity: BaseActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.parentActivity = activity
            //activity?.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun getBaseActivity() = parentActivity

    interface CallBack {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }

    open fun showProgressDialog(
    ) {
        // if (isActivityPaused) return
        val adb = AlertDialog.Builder(requireContext())
        val view: View =
            layoutInflater.inflate(R.layout.custom_progress_bar, null)
        val tv = view.findViewById<View>(R.id.idTextViewMessage) as TextView
        tv.text = resources.getString(R.string.loading)
        adb.setView(view)
        alertDialogProgressBar = adb.create()
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
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    fun showAlertDialog(dialogBuilder: AlertDialog.Builder.() -> Unit) {
        val builder = AlertDialog.Builder(getBaseActivity())
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
            Log.e(this.getBaseActivity()?.localClassName, message)
    }

    fun logDebug(message: String) {
        if (BuildConfig.DEBUG)
            Log.d(this.getBaseActivity()?.localClassName, message)
    }

    fun logInfo(message: String) {
        if (BuildConfig.DEBUG)
            Log.i(this.getBaseActivity()?.localClassName, message)
    }
}