package com.example.losdelaheroica.addSong.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SongEntity::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
}