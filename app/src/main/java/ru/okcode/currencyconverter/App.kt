package ru.okcode.currencyconverter

import android.app.Application
import android.content.Context
import timber.log.Timber

class App : Application() {


    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        private lateinit var appContext: Context

        fun getAppContext(): Context {
            return appContext
        }
    }
}