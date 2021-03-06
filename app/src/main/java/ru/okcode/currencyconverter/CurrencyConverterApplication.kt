package ru.okcode.currencyconverter

import android.app.Application
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import com.yandex.metrica.push.YandexMetricaPush
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class CurrencyConverterApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Creating an extended library configuration.
        val config: YandexMetricaConfig =
            YandexMetricaConfig.newConfigBuilder(BuildConfig.YA_KEY).build()
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(applicationContext, config)
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this)
        // Yandex Push init

        YandexMetricaPush.init(applicationContext)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
//        else {
//            Timber.plant(CrashReportingTree())
//        }
    }
}