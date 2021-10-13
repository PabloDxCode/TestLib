package com.pablogd.componentbox

import android.app.Activity
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

class AlertBottomFactory(private var view: View?, private val activity: Activity?) {

    private fun baseSnackBar(message: String): Snackbar {
        if (view == null) {
            view = activity!!.window.decorView.rootView
        }
        val snackBar = Snackbar.make(view!!, message, Snackbar.LENGTH_LONG)
        activity?.let {
            snackBar.setActionTextColor(ContextCompat.getColor(it, R.color.bottomAlert_textColor))
        }
        return snackBar
    }

    fun showCommonMessage(message: Int) {
        activity?.let {
            showCommonMessage(it.getString(message))
        }
    }

    fun showCommonMessage(message: String){
        activity?.let {
            val snackBar = baseSnackBar(message)
            snackBar.view.setBackgroundColor(ContextCompat.getColor(it, R.color.bottomAlert_commonBackground))
            snackBar.show()
        }
    }

    fun showAlertMessage(message: Int) {
        activity?.let {
            showAlertMessage(it.getString(message))
        }
    }

    fun showAlertMessage(message: String){
        activity?.let {
            val snackBar = baseSnackBar(message)
            snackBar.view.setBackgroundColor(ContextCompat.getColor(it, R.color.bottomAlert_alertBackground))
            snackBar.show()
        }
    }

    fun showErrorMessage(message: Int) {
        activity?.let {
            showErrorMessage(it.getString(message))
        }
    }

    fun showErrorMessage(message: String){
        activity?.let {
            val snackBar = baseSnackBar(message)
            snackBar.view.setBackgroundColor(ContextCompat.getColor(it, R.color.bottomAlert_errorBackground))
            snackBar.show()
        }
    }

}