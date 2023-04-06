package com.gca.mygca.retrofit

import com.gca.mygca.model.request.DepartureRequestModel
import com.gca.mygca.model.SliderResponseModel
import com.gca.mygca.model.request.CommonRequestModel
import com.gca.mygca.model.request.StudentClassRequestModel
import com.gca.mygca.model.request.TransportFeesRequestModel
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
    suspend fun getDeparture(): DepartureResponseModel

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

    @POST("ReadSchoolFees.php")
    suspend fun getSchoolFees(@Body commonRequestModel: CommonRequestModel): MyApiResponse<SchoolFeesResponseModel>

}