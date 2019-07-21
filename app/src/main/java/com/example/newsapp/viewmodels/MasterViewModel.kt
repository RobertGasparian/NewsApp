package com.example.newsapp.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.newsapp.app.util.DataFormatUtil
import com.example.newsapp.data.managers.NewsRepository
import com.example.newsapp.data.models.INews
import com.example.newsapp.network.tools.Resource
import com.example.newsapp.ui.fragments.PreviewMode
import com.example.newsapp.viewmodels.delegates.IMasterVM
import com.example.newsapp.viewmodels.delegates.IPreviewVM

class MasterViewModel(application: Application) : AndroidViewModel(application), IMasterVM, IPreviewVM {

    private lateinit var mergedNewsListLD: LiveData<Resource<List<INews>>>
    private var modeLD = MutableLiveData<PreviewMode>()
    private var previewXMLString: LiveData<String> = Transformations.switchMap(modeLD) {
        generateXMLPreview(mergedNewsListLD.value?.data ?: emptyList())
    }
    private var previewJSONString: LiveData<String> = Transformations.switchMap(modeLD) {
        generateJSONPreview(mergedNewsListLD.value?.data ?: emptyList())
    }

    override fun getMergedNewsList() : LiveData<Resource<List<INews>>> {
        mergedNewsListLD = NewsRepository.getMergeNews()
        return mergedNewsListLD
    }

    private fun generateXMLPreview(newsList: List<INews>) : LiveData<String> {
        val previewLD = MutableLiveData<String>()
        previewLD.value = DataFormatUtil.newsToXML(newsList)
        return previewLD
    }

    private fun generateJSONPreview(newsList: List<INews>) : LiveData<String> {
        val previewLD = MutableLiveData<String>()
        previewLD.value = DataFormatUtil.newsToJson(newsList)
        return previewLD
    }

    override fun setMode(mode: PreviewMode) {
        modeLD.value = mode
    }

    override fun save(context: Context) : Boolean{
        when(modeLD.value) {
            PreviewMode.JSON -> { return saveJSON(context) }
            PreviewMode.XML -> { return saveXML(context) }
        }
        return false
    }

    private fun saveXML(context: Context) : Boolean {
        mergedNewsListLD.value?.data?.let {
            return NewsRepository.saveXML(context, it)
        }
        return false
    }

    private fun saveJSON(context: Context) : Boolean {
        mergedNewsListLD.value?.data?.let {
            return NewsRepository.saveJSON(context, it)
        }
        return false
    }

    override fun getPreviewXMLString(): LiveData<String> {
        return previewXMLString
    }

    override fun getPreviewJSONString(): LiveData<String> {
        return previewJSONString
    }
}