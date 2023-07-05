package com.gca.mygca.ui

import com.gca.mygca.database.DatabaseManager
import com.gca.mygca.model.SliderResponseModel
import com.gca.mygca.model.request.*
import com.gca.mygca.model.response.*
import com.gca.mygca.retrofit.HomeApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(val homeApiService: HomeApiService,val databaseManager: DatabaseManager) {

    suspend fun addDeparture(departureRequestModel: DepartureRequestModel): Flow<CommonResponseModel> = flow {
      val response = homeApiService.addDeparture(departureRequestModel)
        emit(response)
    }

    suspend fun getBanner(): Flow<SliderResponseModel> = flow {
        val response = homeApiService.getBanners()
        emit(response)
    }

    suspend fun getMedia(commonRequestModel: CommonRequestModel): Flow<GalleryResponseModel> = flow {
        val response = homeApiService.getMedia(commonRequestModel)
        emit(response)
    }

    suspend fun getDeparture(readDepartureRequestModel: ReadDepartureRequestModel): Flow<DepartureResponseModel> = flow {
        val response = homeApiService.getDeparture(readDepartureRequestModel)
        emit(response)
    }

    suspend fun getAllDeparture(commonRequestModel: CommonRequestModel): Flow<DepartureResponseModel> = flow {
        val response = homeApiService.getAllDeparture(commonRequestModel)
        emit(response)
    }

    suspend fun getSchoolBranch(): Flow<SchoolBranchesResponseModel> = flow {
        val response = homeApiService.getSchoolBranch()
        if (response.status==1){
            databaseManager.addBranches(response.result)
        }
        emit(response)
    }

    suspend fun getTransport(): Flow<SchoolTransportResponseModel> = flow {
        val response = homeApiService.getTransport()
        emit(response)
    }

    suspend fun getTransportRoutes(commonRequestModel: CommonRequestModel): Flow<SchoolTransportResponseModel> = flow {
        val response = homeApiService.getTransportRoutes(commonRequestModel)
        emit(response)
    }

    suspend fun getTransportFees(transportFeesRequestModel: TransportFeesRequestModel): Flow<TransportFeesResponseModel> = flow {
        val response = homeApiService.getTransportFees(transportFeesRequestModel)
        emit(response)
    }

    suspend fun getStudentBoard(): Flow<MyApiResponse<StudentBoardResponseModel>> = flow {
        val response = homeApiService.getStudentBoard()
        if (response.status==1){
            databaseManager.addBoard(response.result)
        }
        emit(response)
    }

    suspend fun getStudentMedium(commonRequestModel: CommonRequestModel): Flow<MyApiResponse<StudentMediumResponseModel>> = flow {
        val response = homeApiService.getStudentMedium(commonRequestModel)
        emit(response)
    }

    suspend fun getStudentClass(studentClassRequestModel: StudentClassRequestModel): Flow<MyApiResponse<StudentMediumResponseModel>> = flow {
        val response = homeApiService.getStudentClass(studentClassRequestModel)
        emit(response)
    }

    suspend fun getStudentRbseClass(studentClassRequestModel: StudentClassRequestModel): Flow<MyApiResponse<StudentMediumResponseModel>> = flow {
        val response = homeApiService.getStudentRbseClass(studentClassRequestModel)
        emit(response)
    }

    suspend fun getSchoolFees(commonRequestModel: CommonRequestModel): Flow<MyApiResponse<SchoolFeesResponseModel>> = flow {
        val response = homeApiService.getSchoolFees(commonRequestModel)
        emit(response)
    }

    suspend fun getAllSchoolFees(): Flow<MyApiResponse<SchoolFeesResponseModel>> = flow {
        val response = homeApiService.getAllSchoolFees()
        emit(response)
    }

    suspend fun manageTransportRoutes(addRequestModel: AddRequestModel): Flow<CommonResponseModel> = flow {
        val response = homeApiService.manageTransportRoutes(addRequestModel)
        emit(response)
    }

    suspend fun manageMedia(addMediaRequestModel: AddMediaRequestModel): Flow<CommonResponseModel> = flow {
        val response = homeApiService.manageMedia(addMediaRequestModel)
        emit(response)
    }

    suspend fun manageSlider(addSliderRequestModel: AddSliderRequestModel): Flow<CommonResponseModel> = flow {
        val response = homeApiService.manageSlider(addSliderRequestModel)
        emit(response)
    }
}