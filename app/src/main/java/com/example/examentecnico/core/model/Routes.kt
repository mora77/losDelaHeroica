package com.example.losdelaheroica.core.model

sealed class Routes(val idRoute: String) {
    object LoginScreen : Routes("loginScreen")
    object SongScreen : Routes("addSongScreen")
}