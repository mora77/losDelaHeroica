package com.example.losdelaheroica.addSong.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.losdelaheroica.addSong.ui.model.Rhythm

@Entity
data class SongEntity(
    @PrimaryKey
    val id: Int,
    val songName: String,
    val songTone: Int,
    val selected: Boolean = false,
    val rhythm: String
)