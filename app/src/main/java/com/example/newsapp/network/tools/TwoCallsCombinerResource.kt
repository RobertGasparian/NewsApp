package com.example.newsapp.network.tools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class TwoCallsCombinerResource<FirstType, SecondType, OutputType>(needLoading: Boolean = true) {
    private val outputLiveData: MutableLiveData<Resource<OutputType>> = MutableLiveData()
    private var firstCallRes: Resource<FirstType> = Resource.Loading(data = null)
    private var secondCallRes: Resource<SecondType> = Resource.Loading(data = null)

    init {
        if (needLoading) {
            outputLiveData.value = Resource.Loading(data = null)
        }
        this.createCall1()
        this.createCall2()
    }

    protected abstract fun createCall1()

    protected abstract fun createCall2()

    fun setValue1(value: Resource<FirstType>) {
        firstCallRes = value
        onFirstValueEmit(value)
        checkForOutput()
    }

    fun setValue2(value: Resource<SecondType>) {
        secondCallRes = value
        onSecondValueEmit(value)
        checkForOutput()
    }

    private fun checkForOutput() {
        if (firstCallRes is Resource.Loading || secondCallRes is Resource.Loading) {
            return
        } else {
            if (firstCallRes is Resource.Success && secondCallRes is Resource.Success) {
                setOutputValue(generateSuccessOutputValue(
                    firstCallRes as Resource.Success<FirstType>,
                    secondCallRes as Resource.Success<SecondType>)
                )
            } else {
                var message: String
                val firstMessage = firstCallRes.message
                val secondMessage = secondCallRes.message
                message = if(firstMessage != null && secondMessage != null) {
                    "$firstMessage and $secondMessage"
                } else if (firstMessage == null) {
                    secondMessage!!
                } else {
                    firstMessage!!
                }
                setOutputValue(Resource.Error(message = message))
            }
        }

    }

    abstract fun generateSuccessOutputValue(
        firstRes: Resource.Success<FirstType>,
        secondRes: Resource.Success<SecondType>
    ) : Resource.Success<OutputType>

    open fun onFirstValueEmit(value: Resource<FirstType>) {}

    open fun onSecondValueEmit(value: Resource<SecondType>) {}

    open fun onOutputValueEmit(value: Resource<OutputType>) {}

    private fun setOutputValue(value: Resource<OutputType>) {
        outputLiveData.value = value
        onOutputValueEmit(value)
    }

    fun asLiveData(): LiveData<Resource<OutputType>> {
        return outputLiveData
    }
}