# ads2_sdk_i SDK 接入文档



## 1. 集成配置

### 1.1 添加ads2_sdk_i 依赖

在 `app/build.gradle` 中添加依赖：

```kotlin
dependencies {
    implementation("com.github.646113969:ads2_sdk_i:v1.0.2")
}
```

在根目录的build.gradle中添加依赖

```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}
```

### 1.2 AndroidManifest 配置

SDK 需要网络权限，确保 `AndroidManifest.xml` 中已声明：

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### 1.3 混淆配置

如果你的项目开启了代码混淆（ProGuard / R8），在混淆配置文件中添加以下规则，防止 SDK 类被混淆：

```proguard
-keep class com.s987j.interweb.ads.** { *; }
-dontwarn com.s987j.interweb.ads.**
```

---

## 3. 快速开始

以下是最简接入流程，只需三步：**初始化 → 加载 → 展示**。

### Step 1：初始化 SDK

在 `Application` 的 `onCreate()` 中完成初始化（整个 App 生命周期只需初始化一次）：

```kotlin
Ads2Sdk_I_Helper.init(context, "从平台获取的应用 ID", "从平台获取的应用 Token")
```

### Step 2：加载广告

在合适的时机（如进入某个页面时）提前加载广告：

```kotlin
Ads2Sdk_I_Helper.loadAd("广告位 ID，从平台获取", "广告主的应用包名", "用户所在国家(CHN)")
```

### Step 3：展示广告

在合适时机（如用户触发某个操作后）检查并展示广告：

```kotlin
Ads2Sdk_I_Helper.showAd(context)
```

---
