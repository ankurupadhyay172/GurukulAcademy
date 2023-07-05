package com.gca.mygca.ui

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.liveData
import com.gca.mygca.base.BaseViewModel
import com.gca.mygca.model.request.*
import com.gca.mygca.model.response.Branches
import com.gca.mygca.model.response.SchoolBranchesResponseModel
import com.gca.mygca.utils.toLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds


@HiltViewModel
class HomeViewModel @Inject constructor(val repository: HomeRepository): BaseViewModel() {

    var selectedBranch:String? = "0"
    var selectedBranchModel:Branches? = null
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

    fun getDeparture(readDepartureRequestModel: ReadDepartureRequestModel) = liveData(Dispatchers.IO){
        repository.getDeparture(readDepartureRequestModel).toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun getAllDeparture(commonRequestModel: CommonRequestModel) = liveData(Dispatchers.IO){
        repository.getAllDeparture(commonRequestModel).toLoadingState().catch {  }.collect{
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

    fun getStudentRbseClass(studentClassRequestModel: StudentClassRequestModel) = liveData(Dispatchers.IO){
        repository.getStudentRbseClass(studentClassRequestModel).toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun getSchoolFees(commonRequestModel: CommonRequestModel) = liveData(Dispatchers.IO){
        repository.getSchoolFees(commonRequestModel).toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun getAllSchoolFees() = liveData(Dispatchers.IO){
        repository.getAllSchoolFees().toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun manageTransportRoute(addRequestModel: AddRequestModel) = liveData(Dispatchers.IO){
        repository.manageTransportRoutes(addRequestModel).toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun manageMedia(addMediaRequestModel: AddMediaRequestModel) = liveData(Dispatchers.IO){
        repository.manageMedia(addMediaRequestModel).toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun manageSlider(addSliderRequestModel: AddSliderRequestModel) = liveData(Dispatchers.IO){
        repository.manageSlider(addSliderRequestModel).toLoadingState().catch {  }.collect{
            emit(it)
        }
    }

    fun BitMapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }


    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    val instance = Calendar.getInstance()
    var selectedDate:String? = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(instance.time)
    fun decrementDate():String?{
        instance.add(Calendar.DATE,-1)
        selectedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(instance.time)
        return selectedDate
    }

    fun incrementDate():String?{
        instance.add(Calendar.DATE,1)
        selectedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(instance.time)
        return selectedDate
    }
    fun getCurrentDate(): String?{
        Log.d("mylogdate", "getCurrentDate: "+Date().time)
        selectedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date().time)
        return selectedDate
    }

    fun findElement(list: List<Int>,t:Int):Int{
        var l = 0
        var h = list.size-1

        while (l<=h){
            val m = l+(h-l)/2
            if(list[m]==t) return list[m]
            else if(list[m]<t){
                l = m+1
            }else{
                h = m-1
            }
        }
        return -1
    }
}