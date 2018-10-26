package com.example.sjj.androidlibs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import sjj.alog.Log
import sjj.permission.util.requestPermissionAll
import sjj.rx.destroy

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissionAll(this, {
            Log.e("hi gr")
        }, {
            Log.e("de $it")
        })

        Observable.create<String> { }.doOnDispose {
            Log.e("dispose", Exception())
        }.subscribe().destroy(lifecycle = lifecycle)
    }

    override fun onResume() {
        super.onResume()
    }

}
