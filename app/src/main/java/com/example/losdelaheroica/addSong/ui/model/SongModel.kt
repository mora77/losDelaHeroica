package com.example.losdelaheroica.addSong.ui.model

data class SongModel(
    val id: Long = System.currentTimeMillis(),
    val songName: String,
    val songTone: String,
    val selected: Boolean = false
)