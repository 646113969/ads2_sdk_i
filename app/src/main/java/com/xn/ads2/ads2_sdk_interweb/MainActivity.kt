package com.xn.ads2.ads2_sdk_interweb

import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.xn.ads2.ads2_sdk_interweb.ui.theme.Ads2_Sdk_InterWebTheme
import com.xn.ads2.interweb_h.Ads2Sdk_I_Helper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ads2_Sdk_InterWebTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        Ads2Sdk_I_Helper.init(this@MainActivity, "UQ53HAHV", "9ixch9daqp548287")

        Handler().postDelayed(
            {Ads2Sdk_I_Helper.loadAd("54a226b0-16c4-11f1-8591-c9000fa20814", "com.lazada.android", "CHN")},
            3000)

        Handler().postDelayed({Ads2Sdk_I_Helper.showAd(this@MainActivity)}, 10000)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ads2_Sdk_InterWebTheme {
        Greeting("Android")
    }
}