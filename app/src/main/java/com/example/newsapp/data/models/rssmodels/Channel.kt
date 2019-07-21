package com.example.newsapp.data.models.rssmodels

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(name = "channel", strict = false)
class Channel : Serializable {
    @field:Element(name = "image")
    var image: Image? = null

    @field:Element(name = "copyright")
    var copyright: String? = null

    @field:ElementList(inline = true, name = "item")
    var items: List<Item>? = null

    @field:Element(name = "lastBuildDate")
    var lastBuildDate: String? = null

    @field:Element(name = "link")
    var link: String? = null

    @field:Element(name = "description")
    var description: String? = null

    @field:Element(name = "generator")
    var generator: String? = null

    @field:Element(name = "language")
    var language: String? = null

    @field:Element(name = "title")
    var title: String? = null

    @field:Element(name = "ttl")
    var ttl: String? = null
}