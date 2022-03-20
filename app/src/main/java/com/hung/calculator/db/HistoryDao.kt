package com.hung.calculator.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hung.calculator.model.History

@Dao
interface HistoryDao {

    @Insert
    suspend fun insertHistory(history: History)

    @Query("DELETE FROM history")
    suspend fun deleteAllHistory()

    @Query("SELECT * FROM history")
    fun getAllHistoryWithLiveData(): LiveData<List<History>>


}