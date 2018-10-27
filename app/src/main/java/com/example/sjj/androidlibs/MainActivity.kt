package com.example.sjj.androidlibs

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import sjj.alog.Log
import sjj.permission.util.requestPermissionAll
import sjj.rx.AutoDisposeEnhance
import sjj.rx.destroy
import sjj.serialize.SharedPreferencesDelegate
import sjj.serialize.SharedPreferencesLiveData

class MainActivity : AppCompatActivity(), AutoDisposeEnhance {

    var userName by SharedPreferencesDelegate("") {getSharedPreferences("common", Context.MODE_PRIVATE)}
    val userName2 by SharedPreferencesLiveData("") {getSharedPreferences("common", Context.MODE_PRIVATE)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissionAll(this, {
            Log.e("hi gr")
        }, {
            Log.e("de $it")
        })

        //
        Observable.create<String> { }.doOnDispose {
            Log.e("dispose", Exception())
        }.subscribe().destroy(lifecycle = lifecycle)

        //destroy 方法的参数由 接口 sjj.rx.AutoDisposeEnhance 提供 。
        Observable.create<String> { }.doOnDispose {
            Log.e("dispose2", Exception())
        }.subscribe().destroy()
        userName = "sb"
        userName2.value = "sb_--u"
        userName2.observe(this, Observer {
            Log.e("ob==>$it")
        })
        userName2.observe({lifecycle},{
            Log.e("ob==>$it")
        })
        userName2.value = "sb==2"

    }

    override fun onResume() {
        super.onResume()
    }

}
