package com.pablogd.testlib

import android.app.Application
import com.pablogd.core.Configuration

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Configuration.start()
    }

}