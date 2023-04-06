package com.gca.mygca.ui

import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.liveData
import com.gca.mygca.base.BaseViewModel
import com.gca.mygca.model.request.CommonRequestModel
import com.gca.mygca.model.request.DepartureRequestModel
import com.gca.mygca.model.request.StudentClassRequestModel
import com.gca.mygca.model.request.TransportFeesRequestModel
import com.gca.mygca.utils.toLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import java.io.ByteArrayOutputStream
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(val repository: HomeRepository): BaseViewModel() {

    var selectedBranch:String? = "0"
    fun addDeparture(departureRequestModel: DepartureRequestModel) = liveData(Dispatchers.IO){
        repository.addDeparture(departureRequestModel).toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun getBanner() = liveData(Dispatchers.IO){
        repository.getBanner().toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun getMedia(commonRequestModel: CommonRequestModel) = liveData(Dispatchers.IO){
        repository.getMedia(commonRequestModel).toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun getDeparture() = liveData(Dispatchers.IO){
        repository.getDeparture().toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun getSchoolBranch() = liveData(Dispatchers.IO){
        repository.getSchoolBranch().toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun getTransport() = liveData(Dispatchers.IO){
        repository.getTransport().toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun getTransportRoute(commonRequestModel: CommonRequestModel) = liveData(Dispatchers.IO){
        repository.getTransportRoutes(commonRequestModel).toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun getTransportFees(transportFeesRequestModel: TransportFeesRequestModel) = liveData(Dispatchers.IO){
        repository.getTransportFees(transportFeesRequestModel).toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun getStudentBoard() = liveData(Dispatchers.IO){
        repository.getStudentBoard().toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun getStudentMedium(commonRequestModel: CommonRequestModel) = liveData(Dispatchers.IO){
        repository.getStudentMedium(commonRequestModel).toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun getStudentClass(studentClassRequestModel: StudentClassRequestModel) = liveData(Dispatchers.IO){
        repository.getStudentClass(studentClassRequestModel).toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun getSchoolFees(commonRequestModel: CommonRequestModel) = liveData(Dispatchers.IO){
        repository.getSchoolFees(commonRequestModel).toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun BitMapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
}