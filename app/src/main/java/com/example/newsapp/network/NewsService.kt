package com.example.newsapp.network

import com.example.newsapp.network.responses.NewsResponse
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("top-headlines")
    fun getNewsFeed() : Call<NewsResponse>
}