package com.example.newsapp.network

import com.example.newsapp.network.responses.RssResponse
import retrofit2.Call
import retrofit2.http.GET

interface RssService {
    @GET("rss.xml")
    fun getRssFeed() : Call<RssResponse>
}