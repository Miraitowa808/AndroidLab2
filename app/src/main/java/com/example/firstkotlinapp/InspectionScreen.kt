package com.example.firstkotlinapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun InspectionScreen(viewModel: InspectionViewModel = viewModel()) {
    Column(
        Modifier.fillMaxSize().statusBarsPadding()
            .navigationBarsPadding().padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // 1. 把老师要求的数据全部显示出来（A203, 10, 3, 7）
        Text("校园巡检")
        Text("巡检员：钟鑫平")
        Text("实验室：A203")
        Text("设备总数：10")
        Text("已检查：3")
        Text("未检查：7") // 10 - 3 = 7

        // 2. 按钮依然是你的 OkHttp 逻辑
        Button(enabled = !viewModel.busy, onClick = {
            // 这里把 A203、10、3、7 作为参数传给服务器验证
            viewModel.request("https://httpbin.org/get?room=A203&total=10&checked=3&remain=7")
        }) {
            Text("请求 A203 并回显")
        }
        Button(enabled = !viewModel.busy, onClick = {
            // 注意这里网址里的 room 变成了 A204
            viewModel.request("https://httpbin.org/get?room=A204&total=10&checked=3&remain=7")
        }) {
            Text("请求 A204 并回显")
        }

        // 3. 显示请求结果
        Text(viewModel.result)
    }
}