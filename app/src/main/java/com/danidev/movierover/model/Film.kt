package com.danidev.movierover.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

/**
 * @Parcelize   now we can pass this object to an Activity
 */
@Parcelize
data class Film(
    override val id: Int,
    val title: String,
    @DrawableRes val poster: Int, // image id from 'resources'
    val description: String,
    var rating: Float = 0f,
    var isInFavorites: Boolean = false
): Parcelable, Item {
}
