package com.gca.mygca.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gca.mygca.utils.SingleLiveDataEvent

open class BaseViewModel: ViewModel(){
    var errorLiveData: MutableLiveData<SingleLiveDataEvent<String>> = MutableLiveData()
    fun onError(error: String){
        errorLiveData.postValue(SingleLiveDataEvent(error))
    }

    var messageLiveData: MutableLiveData<SingleLiveDataEvent<String>> = MutableLiveData()
    fun showMessage(message: String){
        messageLiveData.postValue(SingleLiveDataEvent(message))
    }

    var loadingLiveData: MutableLiveData<SingleLiveDataEvent<Boolean>> = MutableLiveData()
    fun showLoading(visible: Boolean) {
        loadingLiveData.postValue(SingleLiveDataEvent(visible))
    }
}