package com.pablogd.debugginglog

import android.util.Log
import com.pablogd.core.ConfigLib

object DebuggingLog {

    var tag = DebuggingLog.javaClass.simpleName

    init {
        ConfigLib.init()
    }

    fun info(message: String){
        Log.i("$tag INFO --", message)
    }

    fun debug(message: String){
        Log.d("$tag DEBUG --", message)
    }

    fun error(message: String){
        Log.e("$tag ERROR --", message)
    }

}