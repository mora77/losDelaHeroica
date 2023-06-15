package com.example.losdelaheroica.addSong.ui

import com.example.losdelaheroica.addSong.ui.model.SongModel

sealed interface SongUiState {
    object Loading : SongUiState
    data class Error(val throwable: Throwable) : SongUiState
    data class Success(val songList: List<SongModel>) : SongUiState
}