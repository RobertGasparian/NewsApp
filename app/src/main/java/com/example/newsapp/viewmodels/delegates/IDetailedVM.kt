package com.example.newsapp.viewmodels.delegates

import androidx.lifecycle.LiveData
import com.example.newsapp.data.models.INews

interface IDetailedVM {
    fun setupInitialNewsValue(news: INews?)
    fun getMergedNews() : LiveData<INews>
}