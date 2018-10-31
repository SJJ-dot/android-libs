1、添加依赖
```groovy
implementation 'com.sjianjun:aLog:1.1.4'
```

2、保存日志到文件配置示例（通常情况无需配置）
```
Config config = new Config();
config.holdMultiple = false; //只保存单一级别日志到文件 默认true
config.holdLev = Config.ERROR;//保存到文件的日志级别 默认all
config.hold = true;//开启保存到文件 默认false
// config.dirName = ""  默认log    保存到根目录下的文件夹名字
// config.dir   默认为空 保存到的目标文件夹  优先级高于dirName
config.enable = BuildConfig.LOG; //是否在控制台打印log
Config.init(config); //使配置生效
```