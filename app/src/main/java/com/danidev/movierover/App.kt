package com.danidev.movierover

import android.app.Application
import android.content.res.Configuration
import timber.log.Timber

/**
 * Performs logic that must be done before the first Activity is launched.
 * Allows containing global variables and constants.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // the version is for development
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun onConfigurationChanged ( newConfig : Configuration) {
        super.onConfigurationChanged(newConfig)
        Timber.d("onConfigurationChanged()")
    }

    companion object {
        // a key for launching DetailsFragment
        const val BUNDLE_KEY = "film"

        // movie poster of RV element that was clicked
        var currentDetailsPoster: Int? = null
    }
}