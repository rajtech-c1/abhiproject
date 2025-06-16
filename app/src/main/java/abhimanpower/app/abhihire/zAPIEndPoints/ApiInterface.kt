package abhimanpower.app.abhihire.zAPIEndPoints

import abhimanpower.app.abhihire.contractorModule.modalClass.AddWorkDataResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.ContractorLoginResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.GetAvailableWorkersResponse
import abhimanpower.app.abhihire.loginModule.apiFunctions.LoginResponse
import abhimanpower.app.abhihire.loginModule.modalClass.WorkerLoginResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddContractorResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddWorkerResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetMonthlyStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetOverallStatsResponse
import abhimanpower.app.abhihire.workerModule.modalClass.GetAvailableWorksResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterface {

    @FormUrlEncoded
    @POST("volunteerLogin.php")
    suspend fun validateLogin(
        @Field("MobileNo") mobileNo: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("WorkerLogin.php")
    suspend fun workerLogin(
        @Field("MobileNo") mobileNo: String
    ): WorkerLoginResponse

    @FormUrlEncoded
    @POST("contractorLogin.php")
    suspend fun contractorLogin(
        @Field("MobileNo") mobileNo: String
    ): ContractorLoginResponse

    @FormUrlEncoded
    @POST("Get_OverallStats_ByVolunteer.php")
    suspend fun getOverallStats(
        @Field("volunteer_id") volunteerId: Int
    ): GetOverallStatsResponse

    @FormUrlEncoded
    @POST("Get_MonthStats_ByVolunteer.php")
    suspend fun getMonthlyStats(
        @Field("month") month: String,
        @Field("volunteer_id") volunteerId: Int
    ): GetMonthlyStatsResponse

    @Multipart
    @POST("AddWorker.php")
    suspend fun addWorker(
        @Part("Name") name: RequestBody,
        @Part("MobileNo") mobileNo: RequestBody,
        @Part("DOB") dob: RequestBody,
        @Part("Gender") gender: RequestBody,
        @Part("Street") street: RequestBody,
        @Part("Area") area: RequestBody,
        @Part("Pincode") pincode: RequestBody,
        @Part("State") state: RequestBody,
        @Part("District") district: RequestBody,
        @Part("WorkCategory") workCategory: RequestBody,
        @Part("Experience") experience: RequestBody,
        @Part("VerificationStatus") verificationStatus: RequestBody,
        @Part("ImageSelected") imageSelected: RequestBody,
        @Part("VolunteerId") volunteerId: RequestBody,
        @Part productImage: MultipartBody.Part
    ): AddWorkerResponse

    @Multipart
    @POST("AddContractor.php")
    suspend fun addContractor(
        @Part("Name") name: RequestBody,
        @Part("MobileNo") mobileNo: RequestBody,
        @Part("Email") email: RequestBody,
        @Part("Street") street: RequestBody,
        @Part("Area") area: RequestBody,
        @Part("Pincode") pincode: RequestBody,
        @Part("District") district: RequestBody,
        @Part("State") state: RequestBody,
        @Part("VerificationStatus") verificationStatus: RequestBody,
        @Part("ImageSelected") imageSelected: RequestBody,
        @Part("VolunteerId") volunteerId: RequestBody,
        @Part productImage: MultipartBody.Part
    ): AddContractorResponse

    @Multipart
    @POST("Update_WorkerProfile.php")
    suspend fun updateWorkerProfile(
        @Part("Name") name: RequestBody,
        @Part("MobileNo") mobileNo: RequestBody,
        @Part("DOB") dob: RequestBody,
        @Part("Gender") gender: RequestBody,
        @Part("Street") street: RequestBody,
        @Part("Area") area: RequestBody,
        @Part("Pincode") pincode: RequestBody,
        @Part("State") state: RequestBody,
        @Part("WorkCategory") workCategory: RequestBody,
        @Part("Experience") experience: RequestBody,
        @Part("VerificationStatus") verificationStatus: RequestBody,
        @Part("ImageSelected") imageSelected: RequestBody,
        @Part("ImageURL") imageURL: RequestBody,
        @Part("VolunteerId") volunteerId: RequestBody,
        @Part("WorkerId") workerId: RequestBody,
        @Part productImage: MultipartBody.Part
    ): AddWorkerResponse

    @GET("Get_AvailableWorks.php")
    suspend fun getAvailableWorks(): GetAvailableWorksResponse

    @FormUrlEncoded
    @POST("Get_AvailableWorkers.php")
    suspend fun getAvailableWorkers(
        @Field("WorkCategory") workCategory: Int
    ): GetAvailableWorkersResponse

    @FormUrlEncoded
    @POST("AddWork.php")
    suspend fun addWorkData(
        @Field("Name") name: String,
        @Field("Description") description: String,
        @Field("Area") area: String,
        @Field("Pincode") pincode: String,
        @Field("WorkCategory") workCategory: Int,
        @Field("State") state: Int,
        @Field("District") district: Int,
        @Field("UserId") userId: Int
        ): AddWorkDataResponse


}