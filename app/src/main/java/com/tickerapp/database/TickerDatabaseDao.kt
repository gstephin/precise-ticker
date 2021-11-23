package com.tickerapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tickerapp.ui.ticker.TickerData


@Dao
interface TickerDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: TickerData)

    //@Delete
    //suspend  fun deleteSubscriber(register: RegisterEntity):Int

    @Query("SELECT * FROM Ticker_Data")
    fun getAllData(): LiveData<List<TickerData>>

    @Query("DELETE FROM Ticker_Data")
    suspend fun deleteAll(): Int



}