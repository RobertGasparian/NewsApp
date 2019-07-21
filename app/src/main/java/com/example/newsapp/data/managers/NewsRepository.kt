package com.example.newsapp.data.managers

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.newsapp.R
import com.example.newsapp.app.util.DataFormatUtil
import com.example.newsapp.data.models.INews
import com.example.newsapp.network.NewsService
import com.example.newsapp.network.RssService
import com.example.newsapp.network.responses.NewsResponse
import com.example.newsapp.network.responses.RssResponse
import com.example.newsapp.network.tools.Resource
import com.example.newsapp.network.tools.TwoCallsCombinerResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader


object NewsRepository {

    lateinit var newsService: NewsService
    lateinit var rssService: RssService

    fun init(rssService: RssService, newsService: NewsService) {
        this.newsService = newsService
        this.rssService = rssService
    }

    fun getMergeNews(): LiveData<Resource<List<INews>>> {
        return (object : TwoCallsCombinerResource<NewsResponse, RssResponse, List<INews>>() {
            override fun createCall1() {
                newsService.getNewsFeed().enqueue(object : Callback<NewsResponse> {
                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        setValue1(Resource.Error(t.message ?: "Unknown error"))
                    }

                    override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            setValue1(Resource.Success(response.body()!!))
                        } else {
                            setValue1(Resource.Error(response.message()))
                        }
                    }

                })
            }

            override fun createCall2() {
                rssService.getRssFeed().enqueue(object : Callback<RssResponse> {
                    override fun onFailure(call: Call<RssResponse>, t: Throwable) {
                        setValue2(Resource.Error(t.message ?: "Unknown error"))
                    }

                    override fun onResponse(call: Call<RssResponse>, response: Response<RssResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            setValue2(Resource.Success(response.body()!!))
                        } else {
                            setValue2(Resource.Error(response.message()))
                        }
                    }

                })
            }

            override fun generateSuccessOutputValue(
                firstRes: Resource.Success<NewsResponse>,
                secondRes: Resource.Success<RssResponse>
            ): Resource.Success<List<INews>> {
                val data = NewsFeedBuilder.merge(
                    secondRes.data?.channel?.items ?: emptyList(),
                    firstRes.data?.articles ?: emptyList()
                )
                return Resource.Success(data)
            }

            override fun onSecondValueEmit(value: Resource<RssResponse>) {
                InMemoryCache.BBC_THUMBNAIL_URL = value.data?.channel?.image?.url
            }

        }).asLiveData()
    }

    fun saveXML(context: Context, newsList: List<INews>) : Boolean {
        val saveStr = DataFormatUtil.newsToXML(newsList)
        return saveStringToFile(context, saveStr)
    }

    fun saveJSON(context: Context, newsList: List<INews>) : Boolean {
        val saveStr = DataFormatUtil.newsToJson(newsList)
        return saveStringToFile(context, saveStr)
    }

    private fun saveStringToFile(context: Context, string: String) : Boolean {
        val filename = context.getString(R.string.saved_news_file_name)
        context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(string.toByteArray())
        }
        return true
    }

    //MARK: for testing
    fun getSavedFileAsString(context: Context) : String {
        return try {
            val fin = context.openFileInput(context.getString(R.string.saved_news_file_name))
            val reader = BufferedReader(InputStreamReader(fin))
            reader.use {
                it.readText()
            }
        } catch (e: FileNotFoundException) {
            ""
        }

    }
}