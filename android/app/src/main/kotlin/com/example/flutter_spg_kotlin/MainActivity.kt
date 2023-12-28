package com.example.flutter_spg_kotlin

import android.content.Context
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import sharepay.paylibrary.BaseCallbackBean
import sharepay.paylibrary.SarawakAPI
import sharepay.paylibrary.SarawakPay
import sharepay.paylibrary.SarawakPayCallback

class MainActivity: FlutterActivity() , SarawakPayCallback {
    private val METHOD_CHANNEL_NAME = "com.example.flutter_spg_kotlin/main"
    private lateinit var channel: MethodChannel
    private var mFactory: SarawakAPI? = null
    private var dataString = ""

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //GeneratedPluginRegistrant.registerWith(this)
        val delegate: Context = this
        mFactory = SarawakPay.createFactory(delegate)
        channel = MethodChannel(flutterEngine!!.dartExecutor.binaryMessenger, METHOD_CHANNEL_NAME)
        channel.setMethodCallHandler { call, result ->
            if (call.method == "spayPlaceOrder") {
                this.dataString = call.argument<String>("dataString").toString()
                sendRequest()
            }
        }
    }

    private fun sendRequest() {
        println("Exec sendRequest.. ${this.dataString}")
        mFactory?.sendReq(this.dataString, this)
    }

    override fun payResult(p0: BaseCallbackBean?) {
        println("Return flag.. ${p0?.flag}")
        println("Return status.. ${p0?.status}")
    }
}
