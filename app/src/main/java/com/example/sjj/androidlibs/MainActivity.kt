package com.example.sjj.androidlibs

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import sjj.alog.Config
import sjj.alog.Log
import sjj.permission.util.requestPermissionAll
import sjj.rx.AutoDisposeEnhance
import sjj.rx.destroy
import sjj.serialize.SharedPreferencesDelegate
import sjj.serialize.SharedPreferencesLiveData
import java.lang.Exception
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), AutoDisposeEnhance {

    var userName by SharedPreferencesDelegate("") {getSharedPreferences("common", Context.MODE_PRIVATE)}
    val userName2 by SharedPreferencesLiveData("") {getSharedPreferences("common", Context.MODE_PRIVATE)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userName = "sb"
        userName2.value = "sb_--u"
        userName2.observe(this, Observer {
            Log.e("ob==>$it")
        })
        userName2.observe({lifecycle},{
            Log.e("ob==>$it")
        })
        userName2.value = "sb==2"
        val sb = StringBuilder()
        for (i in 0 until 136) {
            sb.append("qwertyuiopasdfghjklzxcvbnm,./;")
        }
        Config().apply {
            hold = true
        }.also {
            Config.init(it)
        }
        Log.e(sb)
    }
}
