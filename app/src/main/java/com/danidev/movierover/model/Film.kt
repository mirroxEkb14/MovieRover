package com.danidev.movierover.model

data class Film(
    val id: Int,
    val title: String,
    val poster: Int, // image id from 'resources'
    val description: String
)
