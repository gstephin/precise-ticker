package com.tickerapp.ui.ticker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/*

Created by steph on 11/23/2021

*/
@Entity(tableName = "Ticker_Data")
class TickerData(
    @PrimaryKey
    var id: Int?,
    @ColumnInfo(name = "price")
    val price: String
)