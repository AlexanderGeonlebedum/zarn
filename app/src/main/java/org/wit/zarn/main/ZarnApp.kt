package org.wit.zarn.main

import android.app.Application
import timber.log.Timber

class ZarnApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("Zarn Application Started")
    }


}