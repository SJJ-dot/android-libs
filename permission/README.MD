[MAVEN](https://bintray.com/sjianjun/maven)
### 用法
1、依赖
```groovy
implementation 'com.sjianjun:permissionUtil:1.1.2'
```
2、代码
```kotlin
        requestPermissionAll(this, {
            Log.e("hi gr")
        }, {
            Log.e("de $it")
        })
```

>request

1、com.android.support:appcompat-v7
