package com.example.newsapp.network.interceptors

import com.example.newsapp.app.ApiConstants
import okhttp3.Interceptor
import okhttp3.Response

class SourceInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder()
                .addQueryParameter(ApiConstants.KEY_SOURCE, ApiConstants.SOURCE).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}