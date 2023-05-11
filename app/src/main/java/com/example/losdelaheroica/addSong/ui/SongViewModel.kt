package com.example.losdelaheroica.addSong.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.losdelaheroica.addSong.ui.model.SongModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor() : ViewModel() {
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    private val _songList = mutableStateListOf<SongModel>()
    val songList: List<SongModel> = _songList

    fun closeDialog() {
        _showDialog.value = false
    }

    fun openDialog() {
        _showDialog.value = true
    }

    fun onSongAdd(song: SongModel) {
        _songList.add(song)
        closeDialog()
    }

    fun onSongSelected(song: SongModel) {
        val index = _songList.indexOf(song)
        _songList[index] = _songList[index].let {
            it.copy(selected = !it.selected)
        }
    }

    fun deleteSong(songModel: SongModel) {
        val song = _songList.find { it.id == songModel.id }
        _songList.remove(song)
    }
}