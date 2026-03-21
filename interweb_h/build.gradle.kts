plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "com.xn.ads2.interweb_h"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // AGP 9.0.0 发布组件配置（KTS 语法调整）
    publishing {
        singleVariant("release") {
            withJavadocJar()
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(files("libs/Interweb-release.aar"))

}

// 发布信息配置（AGP 9.0.0 推荐 afterEvaluate 包裹）
afterEvaluate {
    publishing {
        publications {
            // KTS 中创建 publication 的语法调整
            create("release", MavenPublication::class) {
                from(components["release"]) // KTS 中组件引用用 []

                // 核心发布信息
                groupId = "com.github.xn"
                artifactId = "ads2_sdk_i"
                version = "v1.0.1"
            }
        }

        // 本地仓库配置（KTS 路径写法优化）
        repositories {
            maven {
                url = uri(layout.buildDirectory.dir("repo")) // 推荐写法，兼容多系统
            }
        }
    }
}