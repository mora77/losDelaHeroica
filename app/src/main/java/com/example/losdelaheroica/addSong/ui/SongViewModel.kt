package com.example.losdelaheroica.addSong.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.losdelaheroica.addSong.domain.AddSongUseCase
import com.example.losdelaheroica.addSong.domain.DeleteSongUseCase
import com.example.losdelaheroica.addSong.domain.GetSongsUseCase
import com.example.losdelaheroica.addSong.domain.UpdateSongUseCase
import com.example.losdelaheroica.addSong.ui.SongUiState.*
import com.example.losdelaheroica.addSong.ui.model.SongModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val addSongUseCase: AddSongUseCase,
    private val updateSongUseCase: UpdateSongUseCase,
    private val deleteSongUseCase: DeleteSongUseCase,
    getSongsUseCase: GetSongsUseCase
) : ViewModel() {

    val uiState: StateFlow<SongUiState> = getSongsUseCase().map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    fun closeDialog() {
        _showDialog.value = false
    }

    fun openDialog() {
        _showDialog.value = true
    }

    fun onSongAdd(song: SongModel) {
        closeDialog()
        viewModelScope.launch {
            addSongUseCase(song)
        }
    }

    fun onSongSelected(song: SongModel) {
        Log.i("PRIEBA CANCION", "La cancion ${song.songName} cambió de: ${song.selected} a")
        val valToChange = !song.selected
        Log.i("PRIEBA CANCION2", valToChange.toString())
        viewModelScope.launch {
            updateSongUseCase(song.copy(selected = valToChange))
        }
    }

    fun deleteSong(songModel: SongModel) {
        viewModelScope.launch {
            deleteSongUseCase(songModel)
        }
    }
}