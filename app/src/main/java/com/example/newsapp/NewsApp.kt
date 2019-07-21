package com.example.newsapp

import android.app.Application
import com.example.newsapp.data.managers.NewsRepository
import com.example.newsapp.network.RetrofitClient

class NewsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        NewsRepository.init(RetrofitClient.getRssService(), RetrofitClient.getNewsService())
    }
}