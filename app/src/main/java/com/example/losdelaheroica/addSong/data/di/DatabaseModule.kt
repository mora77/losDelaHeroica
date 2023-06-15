package com.example.losdelaheroica.addSong.data.di

import android.content.Context
import androidx.room.Room
import com.example.losdelaheroica.addSong.data.SongDao
import com.example.losdelaheroica.addSong.data.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideSongDao(songDatabase: TodoDatabase): SongDao {
        return songDatabase.songDao()
    }

    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext: Context): TodoDatabase {
        return Room.databaseBuilder(appContext, TodoDatabase::class.java, "SongDatabase").build()
    }
}