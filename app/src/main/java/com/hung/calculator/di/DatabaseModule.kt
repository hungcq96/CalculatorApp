package com.hung.calculator.di

import android.app.Application
import androidx.room.Room
import com.hung.calculator.db.HistoryDao
import com.hung.calculator.db.HistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideHistoryDB(app:Application): HistoryDatabase{
        return Room.databaseBuilder(
            app.applicationContext,
            HistoryDatabase::class.java,
            "history.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(db: HistoryDatabase): HistoryDao{
        return db.getHistoryDao()
    }
}