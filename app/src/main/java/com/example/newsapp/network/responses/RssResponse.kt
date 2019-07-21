package com.example.newsapp.network.responses

import com.example.newsapp.data.models.rssmodels.Channel
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
class RssResponse {
    @field:Element(name = "channel")
    var channel: Channel? = null

    var version: String? = null
}