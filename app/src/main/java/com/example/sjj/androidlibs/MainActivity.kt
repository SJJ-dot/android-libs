package com.example.sjj.androidlibs

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import sjj.alog.Config
import sjj.alog.Log
import sjj.alog.Logger
import sjj.rx.AutoDisposeEnhance
import sjj.serialize.SharedPreferencesDelegate
import sjj.serialize.SharedPreferencesLiveData

class MainActivity : AppCompatActivity(), AutoDisposeEnhance {

    var userName by SharedPreferencesDelegate("") {getSharedPreferences("common", Context.MODE_PRIVATE)}
    val userName2 by SharedPreferencesLiveData("") {getSharedPreferences("common", Context.MODE_PRIVATE)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Config.getDefaultConfig().hold = true
        val logger = Logger(Config.getDefaultConfig())
        logger.e("aaaa")
        logger.e("aaaa",Exception())
        Log.e("aaa")
        Log.e("aaa",Exception())
    }


}
