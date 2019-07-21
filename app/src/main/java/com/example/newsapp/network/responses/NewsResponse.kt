package com.example.newsapp.network.responses

import com.example.newsapp.data.models.jsonmodels.Article
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class NewsResponse {

    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null
    @SerializedName("articles")
    @Expose
    var articles: List<Article>? = null

}