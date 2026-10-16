package com.example.firstkotlinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstkotlinapp.ui.theme.FirstKotlinAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstKotlinAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var resultText by remember { mutableStateOf("点击下方按钮开始测试") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "姓名：钟鑫平", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "实验二：HTTP / HTTPS 接口对比测试", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                isLoading = true
                resultText = "正在请求 HTTP 接口..."
                scope.launch {
                    resultText = requestUrl("http://httpbin.org/get?room=A203")
                    isLoading = false
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) { Text("请求 HTTP 接口") }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                isLoading = true
                resultText = "正在请求 HTTPS 接口..."
                scope.launch {
                    resultText = requestUrl("https://httpbin.org/get?room=A203")
                    isLoading = false
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) { Text("请求 HTTPS 接口") }

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "请求结果：", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = resultText, fontSize = 13.sp)
    }
}

suspend fun requestUrl(urlString: String): String {
    return withContext(Dispatchers.IO) {
        var conn: HttpURLConnection? = null
        try {
            val url = URL(urlString)
            conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connectTimeout = 10000
            conn.readTimeout = 10000

            val code = conn.responseCode
            val stream = if (code in 200..299) conn.inputStream else conn.errorStream
            val body = stream?.bufferedReader()?.use { it.readText() } ?: ""

            "【成功】HTTP $code\n$body"
        } catch (e: Exception) {
            "【失败】${e.javaClass.simpleName}: ${e.message}"
        } finally {
            conn?.disconnect()
        }
    }
}