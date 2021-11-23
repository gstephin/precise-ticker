package com.tickerapp.ui.ticker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tickerapp.database.RegisterRepository
import java.lang.IllegalArgumentException

class TickerViewModelFactory(
    private val application: Application):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TickerViewModel::class.java)) {
            return TickerViewModel( application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}