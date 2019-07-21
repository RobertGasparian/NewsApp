package com.example.newsapp.data.managers

import com.example.newsapp.data.models.INews
import com.example.newsapp.data.models.jsonmodels.Article
import com.example.newsapp.data.models.rssmodels.Item

object NewsFeedBuilder {
    fun merge(bbcItemList: List<Item>, newsArticles: List<Article>, needToSort : Boolean = true) : List<INews> {
        val mergeList = mutableListOf<INews>()
        bbcItemList.forEach { item ->
            mergeList.add(item)
        }
        newsArticles.forEach { article ->
            mergeList.add(article)
        }
        if (needToSort) {
            mergeList.sortByDescending { it.getIDateTS() }
        }
        return mergeList
    }
}



