package com.example.losdelaheroica.addSong.domain

import com.example.losdelaheroica.addSong.data.SongRepository
import com.example.losdelaheroica.addSong.ui.model.SongModel
import javax.inject.Inject

class DeleteSongUseCase @Inject constructor(private val songRepository: SongRepository) {
    suspend operator fun invoke(song: SongModel) {
        songRepository.delete(song)
    }
}