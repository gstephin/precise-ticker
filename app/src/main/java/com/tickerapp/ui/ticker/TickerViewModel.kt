package com.tickerapp.ui.ticker

import android.app.Application
import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tickerapp.database.TickerRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONArray
import org.json.JSONObject
import java.net.URI


class TickerViewModel(
    private val tickerDataRepository: TickerRepository,
    application: Application
) :
    AndroidViewModel(application), Observable {
    private var mWebSocketClient: WebSocketClient? = null
    private val _tickerData = MutableLiveData<Int>()
     var tickerDataList = tickerDataRepository.dataList

    val tickerData: LiveData<Int>
        get() = _tickerData

    init {
        Log.i("MYTAG", "init")
    }

    fun getData() {

        val uri = URI("wss://api2.poloniex.com")
        val jsonObject = JSONObject()

        jsonObject.put("command", "subscribe")
        jsonObject.put("channel", 1002)

        mWebSocketClient = object : WebSocketClient(uri) {
            override fun onOpen(serverHandshake: ServerHandshake?) {
                Log.i("Websocket", "Opened")
                mWebSocketClient?.send(jsonObject.toString())

            }

            override fun onMessage(s: String?) {

                val jsonArray = JSONArray(s)
                if (jsonArray.length() > 2) {
                    val string = jsonArray[2].toString()
                    val jsonArrayInner = JSONArray(string)
                    val data = jsonArrayInner[0].toString()

                    val tickerObject = TickerData(data.toInt(), jsonArrayInner[1].toString())
                    insert(tickerObject)

                   // _tickerData.postValue(data.toInt())

                    Log.d("message", s.toString())

                }


            }

            override fun onClose(i: Int, s: String, b: Boolean) {
                Log.i("Websocket", "Closed $s")
            }

            override fun onError(e: Exception) {
                Log.i("Websocket", "Error " + e.message)
            }
        }

        mWebSocketClient?.connect()

    }

    private fun insert(user: TickerData): Job = viewModelScope.launch {
        tickerDataRepository.insert(user)
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun onCleared() {
        super.onCleared()
        mWebSocketClient?.close()
    }
}





