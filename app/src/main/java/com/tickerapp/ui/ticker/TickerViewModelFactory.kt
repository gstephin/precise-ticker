package com.tickerapp.ui.ticker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tickerapp.database.TickerRepository

class TickerViewModelFactory(
    private val repository: TickerRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TickerViewModel::class.java)) {
            return TickerViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}