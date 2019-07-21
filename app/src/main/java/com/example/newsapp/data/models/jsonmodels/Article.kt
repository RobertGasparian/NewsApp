package com.example.newsapp.data.models.jsonmodels

import com.example.newsapp.app.util.convertNewsPubDate
import com.example.newsapp.data.models.INews
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*


class Article : INews, Serializable {

    @SerializedName("source")
    @Expose
    var source: Source? = null
    @SerializedName("author")
    @Expose
    var author: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("urlToImage")
    @Expose
    var urlToImage: String? = null
    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String? = null
    @SerializedName("content")
    @Expose
    var content: String? = null


    override fun getITitle(): String {
        return title ?: "No title"
    }

    override fun getIDescription(): String {
        return description ?: "No description"
    }

    override fun getILink(): String {
        return url ?: ""
    }

    override fun getIDateTS(): Long {
        return publishedAt?.convertNewsPubDate()?.time ?: Date().time
    }

    override fun getIThumbNail(): String? {
        return urlToImage
    }

}