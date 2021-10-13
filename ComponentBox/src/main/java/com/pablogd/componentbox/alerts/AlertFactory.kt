package com.pablogd.componentbox.alerts

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import com.pablogd.componentbox.ComponentBoxTheme
import com.pablogd.componentbox.R

class AlertFactory private constructor(private val builder: Builder) {

    fun showAlert() {
        builder.apply {
            val theme = if(theme == ComponentBoxTheme.LIGHT) R.style.Light else R.style.Dark
            val alertBuilder = AlertDialog.Builder(ContextThemeWrapper(activity, theme))
            alertBuilder.setTitle(title)
            alertBuilder.setMessage(message)
            alertBuilder.setPositiveButton(acceptTitle ?: "Aceptar") { dialogInterface, _ ->
                dialogInterface.dismiss()
                onAccept?.invoke()
            }
            cancelTitle?.let {
                alertBuilder.setNegativeButton(it) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    onCancel?.invoke()
                }
            }
            alertBuilder.setCancelable(false)
            alertBuilder.show()
        }
    }

    class Builder(val activity: Activity) {

        var theme: ComponentBoxTheme = ComponentBoxTheme.LIGHT
            private set

        var title: String? = null
            private set

        var message: String? = null
            private set

        var acceptTitle: String? = null
            private set

        var onAccept: (() -> Unit)? = null
            private set

        var cancelTitle: String? = null
            private set

        var onCancel: (() -> Unit)? = null
            private set

        fun setTheme(theme: ComponentBoxTheme) = apply {
            this.theme = theme
        }

        fun setTitle(title: String) = apply {
            this.title = title
        }

        fun setMessage(message: String) = apply {
            this.message = message
        }

        fun setAccept(title: String, action: (() -> Unit)? = null) = apply {
            this.acceptTitle = title
            this.onAccept = action
        }

        fun setCancel(title: String, action: (() -> Unit)? = null) = apply {
            this.cancelTitle = title
            this.onCancel = action
        }

        fun build() = AlertFactory(this)

    }

}