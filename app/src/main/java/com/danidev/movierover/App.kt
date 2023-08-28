package com.danidev.movierover

import android.app.Application
import android.content.res.Configuration
import timber.log.Timber

/**
 * This Application Class is the core class in an Android application that stores all the components,
 * all the Activites, services, providers and other broadcasts.
 *
 * Performs logic that must be done before the first [android.app.Activity] is launched. Allows
 * containing global variables and constants.
 */
class App : Application() {

    /**
     * Is called when the application starts before other application components are created. This
     * method must not be overridden, but it is the best place to initialize global objects.
     */
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
     * Is called by the system when the device configuration changes.
     *
     * @param newConfig is the new device configuration.
     */
    override fun onConfigurationChanged (newConfig : Configuration) {
        super.onConfigurationChanged(newConfig)
        Timber.d(CONFIGURATION_LOG)
    }

    /**
     * Is called when the system has little RAM and the system needs other running applications to
     * slow down.
     *
     * Redefinition is not required.
     */
    override fun onLowMemory() {
        super.onLowMemory()
    }

    companion object {
        /** Value is a key for launching [com.danidev.movierover.fragments.DetailsFragment]. */
        const val BUNDLE_ITEM_KEY = "film"

        /** Value is a key for setting `transitionName` to the poster in
         * [com.danidev.movierover.fragments.DetailsFragment]. */
        const val BUNDLE_TRANSITION_KEY = "transitionName"

        /** Value represents time, during which a user must click the back button for the second time
         * to exit the application in ms. */
        const val TIME_INTERVAL = 2000

        /** Value represents a log/debug message for the moment, when the device configuration is
         * changed. */
        private const val CONFIGURATION_LOG = "onConfigurationChanged()"

        /** Value represents a movie poster of [androidx.recyclerview.widget.RecyclerView] element
         * that was clicked on. */
        var currentDetailsPoster: Int? = null

        /** Value represents the amount of elements in the [androidx.recyclerview.widget.RecyclerView].
         * Used for setting a different `transitionName` to each poster in the
         * [androidx.recyclerview.widget.RecyclerView]*/
        var rvItemsCounter = 1
    }
}