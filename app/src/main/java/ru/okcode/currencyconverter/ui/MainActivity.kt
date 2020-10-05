package ru.okcode.currencyconverter.ui

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.data.repository.ConfigRepository
import ru.okcode.currencyconverter.ui.overview.OverviewFragment
import javax.inject.Inject
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var configRepository: ConfigRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycle.addObserver(configRepository)

        val fragment = OverviewFragment()

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }



}