package com.example.losdelaheroica.addSong.ui.model

data class SongModel(
    val id: Int = System.currentTimeMillis().hashCode(),
    val songName: String,
    val songTone: Int,
    val selected: Boolean = false,
    val rhythm: Rhythm
)

sealed class Rhythm() {
    object Corrido : Rhythm()
    object Zapateado : Rhythm()
    object Cumbia : Rhythm()
    object Huapango : Rhythm()
    object Nortena : Rhythm()
}