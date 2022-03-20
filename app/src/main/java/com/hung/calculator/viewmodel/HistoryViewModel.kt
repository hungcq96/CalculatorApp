package com.hung.calculator.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.hung.calculator.db.HistoryDao
import com.hung.calculator.db.HistoryDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(val historyDao: HistoryDao) : ViewModel(){

    val listHistory = historyDao.getAllHistoryWithLiveData()

    suspend fun deleteHistory(){
        historyDao.deleteAllHistory()
    }
}