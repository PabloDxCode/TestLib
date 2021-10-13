package com.pablogd.componentbox

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.util.Pair
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

@DslMarker
annotation class NavControllerUtilsDsl

/**
 * creates an instance of [NavControllerUtils] by [NavControllerUtils.Builder] using kotlin dsl.
 *
 * @param activity activity instance
 * @param block builder wrapper for permissions params
 */
@NavControllerUtilsDsl
inline fun navController(activity: Activity, _class: Class<*>, block: NavControllerUtils.Builder.() -> Unit) =
        NavControllerUtils.Builder(activity, _class).apply(block).build()

/**
 * NavControllerUtils
 */
class NavControllerUtils private constructor(
        private val activity: Activity,
        _class: Class<*>,
        private val builder: Builder
) {

    /**
     * Transition
     */
    enum class Transition {
        FADE,
        SLIDE_IN,
        SLIDE_OUT,
        NONE;
    }

    init {
        val intent = Intent(activity, _class)
        if (builder.flags > 0) {
            intent.flags = builder.flags
        }
        builder.params?.let {
            it.forEach { entry ->
                when(val value = entry.value){
                    is String -> intent.putExtra(entry.key, value)
                    is Int -> intent.putExtra(entry.key, value)
                    is Boolean -> intent.putExtra(entry.key, value)
                }
            }
        }
        if (builder.shareElements == null) {
            startActivity(intent)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivityTransition(intent)
            } else {
                startActivity(intent)
            }
        }
    }

    /**
     * Method to start activity
     *
     * @param intent intent object
     */
    private fun startActivity(intent: Intent) {
        if (builder.code > 0) {
            if (builder.fragment == null) {
                activity.startActivityForResult(intent, builder.code)
            } else {
                builder.fragment?.startActivityForResult(intent, builder.code)
            }
        } else if (builder.code == 0) {
            activity.startActivity(intent)
        }
        setTransition()
    }

    /**
     * Method to start activity with transition
     *
     * @param intent intent object
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startActivityTransition(intent: Intent) {
        val options = ActivityOptions.makeSceneTransitionAnimation(activity, builder.shareElements)
        activity.startActivity(intent, options.toBundle())
    }

    /**
     * Method to set transition
     */
    private fun setTransition() {
        when (builder.transition) {
            Transition.FADE ->
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            Transition.SLIDE_IN ->
                activity.overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity)
            Transition.SLIDE_OUT ->
                activity.overridePendingTransition(R.anim.slide_in_left_activity, R.anim.slide_out_right_activity)
            Transition.NONE ->
                activity.overridePendingTransition(0, 0)
        }
    }

    @NavControllerUtilsDsl
    class Builder(private val activity: Activity, private val _class: Class<*>) {

        @JvmField
        var flags: Int = 0

        @JvmField
        var params: HashMap<String, Any>? = null

        @JvmField
        var code: Int = 0

        @JvmField
        var fragment: Fragment? = null

        @JvmField
        var transition = Transition.NONE

        @JvmField
        var shareElements: Pair<View, String>? = null

        /**
         * Method to set flags to config intent
         *
         * @param flags to config intent
         *
         * @return this
         */
        fun setFlags(flags: Int): Builder = apply { this.flags = flags }

        /**
         * Method to set params to send to another view
         *
         * @param params to send to another view
         *
         * @return this
         */
        fun setParams(params: HashMap<String, Any>?): Builder = apply { this.params = params }

        /**
         * Method to set code
         *
         * @param code code value
         *
         * @return this
         */
        fun setCode(code: Int): Builder = apply { this.code = code }

        /**
         * Method to set fragment
         *
         * @param fragment fragment instance
         *
         * @return this
         */
        fun setFragment(fragment: Fragment?): Builder = apply { this.fragment = fragment }

        /**
         * Method to set transition
         *
         * @param transition transition type
         *
         * @return this
         */
        fun setTransition(transition: Transition): Builder = apply { this.transition = transition }

        /**
         * Method to set params transition
         *
         * @param shareElements share elements for transition
         *
         * @return this
         */
        fun setParamsTransitionAnimation(shareElements: Pair<View, String>?): Builder =
                apply { this.shareElements = shareElements }

        /**
         * Method to start intent
         */
        fun build() {
            NavControllerUtils(activity, _class, this@Builder)
        }

    }

}