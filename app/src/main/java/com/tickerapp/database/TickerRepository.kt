package com.tickerapp.database

import com.tickerapp.ui.ticker.TickerData

class TickerRepository(private val dao: TickerDatabaseDao) {

    val dataList = dao.getAllData()
    suspend fun insert(data: TickerData) {
        return dao.insert(data)
    }


    //suspend fun deleteAll(): Int {
    //    return dao.deleteAll()
    //}

}