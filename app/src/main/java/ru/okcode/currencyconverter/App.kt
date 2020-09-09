package ru.okcode.currencyconverter

import android.app.Application
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Creating an extended library configuration.
        val config: YandexMetricaConfig = YandexMetricaConfig.newConfigBuilder(BuildConfig.YA_KEY).build()
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(applicationContext, config)
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this)
    }
}