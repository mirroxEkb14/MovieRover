package com.danidev.movierover.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

/**
 * This class represents an object of each movie.
 *
 * Uses the [kotlinx.android.parcel.Parcelize] annotation that allows to pass this object to an
 * [android.app.Activity].
 *
 * @property id is a movie unique id.
 * @property title is a movie title.
 * @property poster is an image id from resources.
 * @property description is a movie description.
 * @property rating is a movie rating.
 * @property isInFavorites is a [Boolean] value representing the state: either this particular movie
 * is in "favorites" or not.
 */
@Parcelize
data class Film(
    override val id: Int,
    val title: String,
    @DrawableRes val poster: Int,
    val description: String,
    var rating: Float = 0f,
    var isInFavorites: Boolean = false
): Parcelable, Item
