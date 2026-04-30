package com.practicum.playlistmaker.data.storage

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.practicum.playlistmaker.data.db.AppDatabase

private val Context.searchHistoryDataStore by preferencesDataStore(name = "search_history")

object StorageProvider {
    @Volatile
    private var appDatabase: AppDatabase? = null

    fun provideDatabase(context: Context): AppDatabase {
        return appDatabase ?: synchronized(this) {
            appDatabase ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "playlist_maker.db",
            )
                .fallbackToDestructiveMigration()
                .build()
                .also { appDatabase = it }
        }
    }

    fun provideSearchHistoryDataStore(context: Context) = context.applicationContext.searchHistoryDataStore
}
