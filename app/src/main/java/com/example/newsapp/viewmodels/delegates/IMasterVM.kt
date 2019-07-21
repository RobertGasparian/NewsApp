package com.example.newsapp.viewmodels.delegates

import androidx.lifecycle.LiveData
import com.example.newsapp.data.models.INews
import com.example.newsapp.network.tools.Resource

interface IMasterVM {
    fun getMergedNewsList() : LiveData<Resource<List<INews>>>
}