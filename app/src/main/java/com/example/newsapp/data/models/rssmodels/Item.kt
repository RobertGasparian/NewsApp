package com.example.newsapp.data.models.rssmodels

import com.example.newsapp.app.util.convertBbcPubDate
import com.example.newsapp.data.models.INews
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.io.Serializable
import java.util.*

@Root(name = "item", strict = false)
class Item : INews, Serializable {

    @field:Element(name = "link")
    var link: String? = null

    @field:Element(name = "description")
    var description: String? = null

    @field:Element(name = "guid")
    var guid: String? = null

    @field:Element(name = "title")
    var title: String? = null

    @field:Element(name = "pubDate")
    var pubDate: String? = null

    override fun getITitle(): String {
        return title ?: "No title"
    }

    override fun getIDescription(): String {
        return description ?: "No description"
    }

    override fun getILink(): String {
        return link ?: ""
    }

    override fun getIDateTS(): Long {
        return pubDate?.convertBbcPubDate()?.time ?: Date().time
    }

    override fun getIThumbNail(): String? {
        return null
    }
}