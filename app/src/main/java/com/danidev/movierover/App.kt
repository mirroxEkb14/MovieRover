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
        const val BUNDLE_ITEM_KEY = "film"

        // a key for setting transitionName to the poster in DetailsFragment
        const val BUNDLE_TRANSITION_KEY = "transitionName"

        // during this time a user must click the back button for the second time to exit the app
        // in ms
        const val TIME_INTERVAL = 2000

        // movie poster of RV element that was clicked
        var currentDetailsPoster: Int? = null

        // represents the amount of elements in the RV
        // used for setting a different transitionName to each poster in the RV
        var rvItemsCounter = 1
    }
}