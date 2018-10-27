[MAVEN](https://bintray.com/sjianjun/maven)
### 用法：
1、添加依赖
```groovy
implementation 'com.sjianjun:rxutils:1.0.2'
```
2、代码
提供了3个Disposable的扩展方法这里使用destory演示。
```kotlin
class MainActivity : AppCompatActivity(), AutoDisposeEnhance {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //
        Observable.create<String> { }.doOnDispose {
            Log.e("dispose", Exception("测试检查调用栈"))
        }.subscribe().destroy(lifecycle = lifecycle)

        //destroy 方法的参数由 接口 sjj.rx.AutoDisposeEnhance 提供 。
        Observable.create<String> { }.doOnDispose {
            Log.e("dispose2", Exception("测试检查调用栈"))
        }.subscribe().destroy()
    }

    override fun onResume() {
        super.onResume()
    }

}
```
>request
1、com.android.support > 27
