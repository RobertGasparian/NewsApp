package com.example.newsapp.app.util

import com.example.newsapp.data.models.INews
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.google.gson.GsonBuilder

object DataFormatUtil {

    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val xmlMapper = XmlMapper()

    fun newsToJson(news: List<INews>): String {
        return gson.toJson(news)
    }

    fun newsToXML(news: List<INews>): String {
        return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(news)
    }
}