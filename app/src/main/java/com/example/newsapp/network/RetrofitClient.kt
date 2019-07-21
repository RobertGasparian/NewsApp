package com.example.newsapp.network

import com.example.newsapp.app.ApiConstants
import com.example.newsapp.network.interceptors.ApiKeyInterceptor
import com.example.newsapp.network.interceptors.SourceInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitClient {
    var client: OkHttpClient? = null

    fun getNewsService() : NewsService {
        if (client == null) {
            client = OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor())
                .addInterceptor(SourceInterceptor())
                .build()
        }

        return Retrofit.Builder()
                .client(client!!)
                .baseUrl(ApiConstants.NEWS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NewsService::class.java)
    }

    fun getRssService() : RssService {
            return Retrofit.Builder()
                    .baseUrl(ApiConstants.BBC_BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build().create(RssService::class.java)
    }
}