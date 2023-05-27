package com.example.borjaweatherapp.network

class ResponseDO : BaseDo() {
    var service: String? = null
    var responseData: Any? = null
    var errorData: Any? = null
    var responseCode: String = ""
    var isError: Boolean = false
}