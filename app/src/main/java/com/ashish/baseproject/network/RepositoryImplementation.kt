package com.ashish.baseproject.network

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import io.reactivex.Flowable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

open class RepositoryImplementation(
    private var apiInterface: ApiInterface,
    private var androidApplication: Application
) {
    lateinit var currentActivity: Activity
    /*fun getSampleData(base: String, activity: Activity): Flow<String> {
        currentActivity = activity
        return flow {
            val users = apiInterface.getData(base).await()
            emit(users.body().toString())
        }.retryWhen { cause, attempt ->
            if (attempt <1) {
                if (handleException(cause)) {
                    emit((cause.toString()))
                    delay(1000) // delay for one second before retry
                  true
                } else {                       // do not retry otherwise
                    false
                }
            } else {
                false
            }
        }
            .catch { throwable ->
                emit((throwable.toString()))
            }
            .flowOn(Dispatchers.Main)

    }*/

    fun getSampleData(activity: Activity): MutableLiveData<String> {
        currentActivity = activity
        val userData = MutableLiveData<String>()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiInterface.getData().await()
                userData.value = response.body().toString()

            } catch (t: Throwable) {
                userData.value = null
                if (handleException(t)) {
                    showAlertDialog {
                        positiveButton {
                            getSampleData(activity) }
                        negativeButton { }
                    }

                }
            }
        }
        return userData
    }


    private fun handleException(cause: Throwable): Boolean {
        var result: Boolean = false
        when (cause) {
            is HttpException -> {
                result = false
            }
            is SocketTimeoutException -> {
                result = false
            }
            is TimeoutException -> {
                result = false
            }
            is UnknownHostException -> {
                result = true
            }
        }
        return result
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        when (exception) {

            is HttpException -> {
                Toast.makeText(
                    androidApplication,
                    (getErrorMessage(exception.code())),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is SocketTimeoutException -> {

                Toast.makeText(
                    androidApplication,
                    getErrorMessage(ErrorCodes.SocketTimeOut.code),
                    Toast.LENGTH_SHORT
                ).show()

            }
            else -> {
                Toast.makeText(
                    androidApplication,
                    (getErrorMessage(Int.MAX_VALUE)),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            401 -> "Unauthorised"
            404 -> "Not found"
            else -> "Something went wrong"
        }
    }

    enum class ErrorCodes(val code: Int) {
        SocketTimeOut(-1)
    }

    private fun showAlertDialog(dialogBuilder: AlertDialog.Builder.() -> Unit) {
        val builder = AlertDialog.Builder(currentActivity)

        builder.dialogBuilder()
        val dialog = builder.create()
        dialog.setTitle("Time out exception.")
        dialog.show()
    }

    private fun AlertDialog.Builder.positiveButton(
        text: String = "Okay",
        handleClick: (which: Int) -> Unit = { true }
    ) {
        this.setPositiveButton(text) { _, which -> handleClick(which) }
    }

    private fun AlertDialog.Builder.negativeButton(
        text: String = "Cancel",
        handleClick: (which: Int) -> Unit = { false }
    ) {
        this.setNegativeButton(text) { _, which -> handleClick(which) }
    }


}