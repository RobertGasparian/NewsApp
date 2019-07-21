package com.example.newsapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.models.INews
import com.example.newsapp.viewmodels.delegates.IDetailedVM

class DetailedViewModel(application: Application) : AndroidViewModel(application), IDetailedVM {

    private val mergedNewsLD = MutableLiveData<INews>()

    //TODO: need to refactor so it gets data from Repo
    override fun setupInitialNewsValue(news: INews?) {
        news?.let {
            mergedNewsLD.value = it
        }
    }

    override fun getMergedNews() : LiveData<INews> {
        return mergedNewsLD
    }

}