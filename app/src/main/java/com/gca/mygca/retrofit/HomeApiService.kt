package com.gca.mygca.retrofit

import com.gca.mygca.model.SliderResponseModel
import com.gca.mygca.model.request.*
import com.gca.mygca.model.response.*
import retrofit2.http.Body
import retrofit2.http.POST

interface HomeApiService {

    @POST("AddDeparture.php")
    suspend fun addDeparture(@Body departureRequestModel: DepartureRequestModel): CommonResponseModel

    @POST("ReadSliders.php")
    suspend fun getBanners(): SliderResponseModel

    @POST("ReadMedia.php")
    suspend fun getMedia(@Body commonRequestModel: CommonRequestModel): GalleryResponseModel

    @POST("ReadDeparture.php")
    suspend fun getDeparture(@Body readDepartureRequestModel: ReadDepartureRequestModel): DepartureResponseModel

    @POST("ReadAllDepartures.php")
    suspend fun getAllDeparture(@Body commonRequestModel: CommonRequestModel): DepartureResponseModel

    @POST("ReadBranches.php")
    suspend fun getSchoolBranch(): SchoolBranchesResponseModel

    @POST("ReadTransport.php")
    suspend fun getTransport(): SchoolTransportResponseModel

    @POST("ReadTransportRoutes.php")
    suspend fun getTransportRoutes(@Body commonRequestModel: CommonRequestModel): SchoolTransportResponseModel

    @POST("ReadTransportFees.php")
    suspend fun getTransportFees(@Body transportFeesRequestModel: TransportFeesRequestModel): TransportFeesResponseModel

    @POST("ReadStudentBoard.php")
    suspend fun getStudentBoard(): MyApiResponse<StudentBoardResponseModel>

    @POST("ReadStudentMedium.php")
    suspend fun getStudentMedium(@Body commonRequestModel: CommonRequestModel): MyApiResponse<StudentMediumResponseModel>

    @POST("ReadStudentClass.php")
    suspend fun getStudentClass(@Body studentClassRequestModel: StudentClassRequestModel): MyApiResponse<StudentMediumResponseModel>

    @POST("ReadStudentRbseClasses.php")
    suspend fun getStudentRbseClass(@Body studentClassRequestModel: StudentClassRequestModel): MyApiResponse<StudentMediumResponseModel>

    @POST("ReadSchoolFees.php")
    suspend fun getSchoolFees(@Body commonRequestModel: CommonRequestModel): MyApiResponse<SchoolFeesResponseModel>

    @POST("ReadAllFees.php")
    suspend fun getAllSchoolFees(): MyApiResponse<SchoolFeesResponseModel>

    @POST("ManageTransportRoutes.php")
    suspend fun manageTransportRoutes(@Body addRequestModel: AddRequestModel): CommonResponseModel

    @POST("ManageMedia.php")
    suspend fun manageMedia(@Body addMediaRequestModel: AddMediaRequestModel): CommonResponseModel

    @POST("ManageSlider.php")
    suspend fun manageSlider(@Body addSliderRequestModel: AddSliderRequestModel): CommonResponseModel



}