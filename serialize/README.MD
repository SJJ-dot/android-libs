[MAVEN](https://bintray.com/sjianjun/maven)
### 用法：
1、添加依赖
```groovy
implementation 'com.sjianjun:serialize:1.0.0'
```
2、代码

```kotlin
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

    }
}
```
>request

1、 "android.arch.lifecycle:livedata:1.1.1"
