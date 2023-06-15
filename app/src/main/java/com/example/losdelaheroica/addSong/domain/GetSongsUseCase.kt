package com.example.losdelaheroica.addSong.domain

import com.example.losdelaheroica.addSong.data.SongRepository
import com.example.losdelaheroica.addSong.ui.model.SongModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSongsUseCase @Inject constructor(private val songRepository: SongRepository) {
    operator fun invoke (): Flow<List<SongModel>> = songRepository.songs
}