package ru.okcode.currencyconverter.ui.basechooser

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.ui.RatesListActivity

@AndroidEntryPoint
class BaseChooserActivity : AppCompatActivity(), BaseChooserFragment.OnOkResultListener {

    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_chooser)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Google AdMob
        MobileAds.initialize(this) {}

        adView = findViewById(R.id.adView)

        adView?.let {
            val adRequest = AdRequest.Builder().build()
            it.loadAd(adRequest)
        }


        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            navigateUpTo()
        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don"t need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the BaseChooser fragment and add it to the activity
            // using a fragment transaction.
            val argCurrencyCode: String =
                intent.getStringExtra(BaseChooserFragment.ARG_CURRENCY_CODE)!!

            val argCurrencyAmount: Float =
                intent.getFloatExtra(BaseChooserFragment.ARG_CURRENCY_AMOUNT, 0f)

            val fragment =
                BaseChooserFragment.newInstance(argCurrencyCode, argCurrencyAmount)

            fragment.setOnOkResultListener(this)

            supportFragmentManager.beginTransaction()
                .add(R.id.base_chooser_container, fragment)
                .commit()
        }
    }

    override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    override fun onResume() {
        adView?.resume()
        super.onResume()
    }

    override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                navigateUpTo()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onBackPressed() {
        navigateUpTo()
    }

    override fun onClickOkResult() {
        navigateUpTo()
    }

    private fun navigateUpTo() {
        navigateUpTo(Intent(this, RatesListActivity::class.java))
    }
}