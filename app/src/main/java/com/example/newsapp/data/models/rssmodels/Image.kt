package com.example.newsapp.data.models.rssmodels

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(name = "image", strict = false)
class Image : Serializable {
    @field:Element(name = "link")
    var link: String? = null

    @field:Element(name = "title")
    var title: String? = null

    @field:Element(name = "url")
    var url: String? = null
}