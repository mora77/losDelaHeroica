package com.example.losdelaheroica.addSong.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {

    @Query("SELECT * from SongEntity")
    fun getSongs(): Flow<List<SongEntity>>

    @Insert
    suspend fun addSong(song: SongEntity)

    @Update
    suspend fun updateSong(song: SongEntity)

    @Delete
    suspend fun deleteSong(song: SongEntity)
}