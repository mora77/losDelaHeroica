package com.example.losdelaheroica.addSong.data

import com.example.losdelaheroica.addSong.ui.model.Rhythm
import com.example.losdelaheroica.addSong.ui.model.SongModel
import com.example.losdelaheroica.addSong.ui.utils.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongRepository @Inject constructor(private val songDao: SongDao) {

    val songs: Flow<List<SongModel>> =
        songDao.getSongs().map { items ->
            items.map { song ->
                val rhythm: Rhythm = Utils.stringToRhythm(song.rhythm)
                SongModel(
                    id = song.id,
                    songName = song.songName,
                    songTone = song.songTone,
                    selected = song.selected,
                    rhythm = rhythm
                )
            }
        }

    suspend fun add(songModel: SongModel) {
        songDao.addSong(
            songModel.toEntity()
        )
    }

    suspend fun update(songModel: SongModel) {
        songDao.updateSong(
            songModel.toEntity()
        )
    }

    suspend fun delete(songModel: SongModel) {
        songDao.deleteSong(
            songModel.toEntity()
        )
    }
}

fun SongModel.toEntity(): SongEntity {
    return SongEntity(
        id = this.id,
        songName = this.songName,
        songTone = this.songTone,
        selected = this.selected,
        rhythm = this.rhythm.toString()
    )
}