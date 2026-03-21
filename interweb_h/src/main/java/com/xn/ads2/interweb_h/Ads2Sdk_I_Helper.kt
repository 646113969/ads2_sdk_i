package com.xn.ads2.interweb_h

import android.app.Activity
import android.content.Context
import android.util.Log
import com.s987j.interweb.ads.InterWebAd
import com.s987j.interweb.ads.InterWebAdsSdk
import com.s987j.interweb.ads.callback.AdLoadCallback
import com.s987j.interweb.ads.callback.AdShowCallback
import com.s987j.interweb.ads.callback.InitCallback
import com.s987j.interweb.ads.config.InterWebAdsConfig
import com.s987j.interweb.ads.model.AdError

object Ads2Sdk_I_Helper {
    private const val TAG = "Ads2Sdk_I_Helper"
    private var interWebAd: InterWebAd? = null
    fun init(context: Context, appId: String, appToken: String) {
        val config = InterWebAdsConfig(
            appId = appId,       // 从平台获取的应用 ID
            appToken = appToken  // 从平台获取的应用 Token
        )

        InterWebAdsSdk.init(context, config, object : InitCallback {
            override fun onSuccess() {
                // SDK 初始化成功，可以开始加载广告
                Log.i(TAG, "Ads2Sdk_I 初始化成功")
            }
            override fun onError(code: Int, message: String) {
                // 初始化失败，检查 appId / appToken 是否正确
                Log.e(TAG, "Ads2Sdk_I 初始化失败: code=$code, message=$message")
            }
        })
    }

    /***
     * @param unitId = "YOUR_UNIT_ID",   // 广告位 ID，从平台获取
     * @param advBundle = "com.xxx.yyy", // 广告主的应用包名
     * @param cou = "IDN",               // 用户所在国家（可选，见 1.1 配置表）
     */
    fun loadAd(unitId: String, advBundle: String, cou: String) {
        InterWebAdsSdk.loadAd(
            unitId = unitId,   // 广告位 ID，从平台获取
            advBundle = advBundle, // 广告主的应用包名
            cou = cou,               // 用户所在国家（可选，见 1.1 配置表）
            callback = object : AdLoadCallback {
                override fun onAdLoaded(ad: InterWebAd) {
                    // 广告加载成功，保存对象，等待展示
                    interWebAd = ad
                }
                override fun onAdLoadFailed(error: AdError) {
                    // 广告加载失败，可查看错误码或稍后重试
                    Log.e(TAG, "Ads2Sdk_I 加载失败: code=${error.code}, msg=${error.message}")
                }
            }
        )
    }

    fun showAd(activity: Activity) {
        if (interWebAd?.isReady() == true) {
            interWebAd?.show(activity, object : AdShowCallback {
                override fun onAdShown() {
                    // 广告已成功展示给用户
                    Log.e(TAG, "Ads2Sdk_I 展示成功")

                }
                override fun onAdShowFailed(error: AdError) {
                    // 展示失败，可重新加载
                    Log.e(TAG, "Ads2Sdk_I 展示失败: code=${error.code}, msg=${error.message}")
                }
                override fun onAdClosed() {
                    // 用户关闭了广告，在此处销毁对象并重新加载
                    interWebAd?.destroy()
                    interWebAd = null
                    // 可在此处提前加载下一条广告
                }
            })
        } else {
            // 广告未就绪（尚未加载完成、已过期等），可重新触发加载
            loadAd("", "", "")
        }
    }
}