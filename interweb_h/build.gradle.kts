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

//    implementation(files("libs/Interweb-release.aar"))
    implementation("com.xn.ads2:interweb-local:1.0.0")

}

// 统一的 publishing 配置（合并重复部分，放到 afterEvaluate 中，AGP 9.0+ 推荐）
afterEvaluate {
    publishing {
        publications {
            // 1. 发布本地 AAR 到 Maven 本地仓库
            create("interwebLocal", MavenPublication::class) {
                groupId = "com.xn.ads2"
                artifactId = "interweb-local"
                version = "1.0.0"
                artifact(file("libs/Interweb-release.aar"))
            }

            // 2. 发布当前库的 release 版本（仅定义一次，避免重复）
            create("release", MavenPublication::class) {
                from(components["release"])
                groupId = "com.github.xn"
                artifactId = "ads2_sdk_i"
                version = "v1.0.2"
            }
        }

        // 配置发布仓库（合并重复配置，仅定义一次）
        repositories {
            mavenLocal() // 优先发布到本地 Maven 仓库（供依赖引用）
            maven {
                url = uri(layout.buildDirectory.dir("repo")) // JitPack 本地仓库
            }
        }
    }
}