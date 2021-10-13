package com.pablogd.componentbox.alerts

import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.InputFilter
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import com.pablogd.componentbox.AlertBottomFactory
import com.pablogd.componentbox.ComponentBoxTheme
import com.pablogd.componentbox.R
import com.pablogd.componentbox.databinding.AlertCustomFieldBinding

class FieldAlertFactory private constructor(private val builder: Builder) {

    fun showAlert(isPassword: Boolean = false) {
        builder.apply {
            val alertBuilder =
                AlertDialog.Builder(ContextThemeWrapper(activity, R.style.BaseAppTheme))
            val layoutInflater =
                activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater.inflate(R.layout.alert_custom_field, null)
            val binding = AlertCustomFieldBinding.bind(view)

            alertBuilder.setView(binding.root)
            alertBuilder.setCancelable(false)

            val alert = alertBuilder.create()
            setTheme(binding, theme)

            title?.let { binding.tvAlertTitle.text = it }
            hint?.let { binding.etAlertField.hint = it }
            value?.let { binding.etAlertField.setText(it) }

            configRules(binding, isPassword)
            binding.apply {
                btAccept.text = acceptTitle
                btCancel.text = cancelTitle
                btAccept.setOnClickListener {
                    val value = etAlertField.text.toString()
                    if (isPassword && value.length != 6 || !isPassword && value.length < 4) {
                        AlertBottomFactory(view, activity).showAlertMessage(error ?: "")
                        return@setOnClickListener
                    }
                    onAccept?.invoke(etAlertField.text.toString())
                    alert.dismiss()
                }
                btCancel.setOnClickListener { alert.dismiss() }
            }
            alert.show()
        }
    }

    private fun setTheme(binding: AlertCustomFieldBinding, theme: ComponentBoxTheme) =
        with(binding) {
            val themeSelected = when (theme) {
                ComponentBoxTheme.LIGHT -> R.style.LightButton
                ComponentBoxTheme.DARK -> R.style.DarkButton
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                btAccept.setTextAppearance(themeSelected)
            } else {
                btAccept.setTextAppearance(builder.activity, themeSelected)
            }
            when (theme) {
                ComponentBoxTheme.LIGHT -> {
                    tvAlertTitle.setTextColor(
                        ContextCompat.getColor(
                            builder.activity,
                            R.color.textColor
                        )
                    )
                    clBackground.setBackgroundColor(
                        ContextCompat.getColor(
                            builder.activity,
                            R.color.background
                        )
                    )
                    etAlertField.setTextColor(
                        ContextCompat.getColor(
                            builder.activity,
                            R.color.textColor
                        )
                    )
                    etAlertField.setHintTextColor(
                        ContextCompat.getColor(
                            builder.activity,
                            R.color.textColor
                        )
                    )
                }
                ComponentBoxTheme.DARK -> {
                    tvAlertTitle.setTextColor(
                        ContextCompat.getColor(
                            builder.activity,
                            R.color.textColor_Dark
                        )
                    )
                    clBackground.setBackgroundColor(
                        ContextCompat.getColor(
                            builder.activity,
                            R.color.background_Dark
                        )
                    )
                    etAlertField.setTextColor(
                        ContextCompat.getColor(
                            builder.activity,
                            R.color.textColor_Dark
                        )
                    )
                    etAlertField.setHintTextColor(
                        ContextCompat.getColor(
                            builder.activity,
                            R.color.textColor_Dark
                        )
                    )
                }
            }
        }

    private fun configRules(binding: AlertCustomFieldBinding, isPassword: Boolean) = with(binding) {
        val fArray = arrayOfNulls<InputFilter>(1)
        if (isPassword) {
            fArray[0] = InputFilter.LengthFilter(6)
            etAlertField.inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
            etAlertField.transformationMethod = PasswordTransformationMethod()
            etAlertField.letterSpacing = 1F
        } else {
            fArray[0] = InputFilter.LengthFilter(25)
            etAlertField.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        }
        etAlertField.filters = fArray
    }

    class Builder(val activity: Activity) {

        var theme: ComponentBoxTheme = ComponentBoxTheme.LIGHT
            private set

        var title: String? = null
            private set

        var hint: String? = null
            private set

        var value: String? = null
            private set

        var error: String? = null
            private set

        var acceptTitle: String? = null
            private set

        var onAccept: ((String) -> Unit)? = null
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

        fun setHint(hint: String) = apply {
            this.hint = hint
        }

        fun setValue(value: String) = apply {
            this.value = value
        }

        fun setError(error: String) = apply {
            this.error = error
        }

        fun setAccept(title: String, action: ((String) -> Unit)? = null) = apply {
            this.acceptTitle = title
            this.onAccept = action
        }

        fun setCancel(title: String, action: (() -> Unit)? = null) = apply {
            this.cancelTitle = title
            this.onCancel = action
        }

        fun build() = FieldAlertFactory(this)

    }

}