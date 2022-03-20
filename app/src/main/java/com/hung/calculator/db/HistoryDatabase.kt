package com.hung.calculator.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hung.calculator.model.History

@Database(entities = [History::class], exportSchema = false, version = 1)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao

//    companion object {
//        private var instance: HistoryDatabase? = null
//        @Synchronized
//        fun getInstance(context: Context): HistoryDatabase {
//            if (instance == null) {
//                instance =
//                    Room.databaseBuilder(context, HistoryDatabase::class.java, "history.db").build()
//            }
//            return instance!!
//        }
//    }
}