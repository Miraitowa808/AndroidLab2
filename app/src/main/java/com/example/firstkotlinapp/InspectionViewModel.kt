package com.example.firstkotlinapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.firstkotlinapp.fetch
class InspectionViewModel : ViewModel() {
    var result by mutableStateOf("等待请求")
        private set
    var busy by mutableStateOf(false)
        private set

    fun request(address: String) {
        busy = true
        result = "请求中：$address"

        viewModelScope.launch {
            // 因为老师的方法里是阻塞的，必须放在 IO 线程
            val response = withContext(Dispatchers.IO) {
                fetch(address)
            }
            result = response
            busy = false
        }
    }
}