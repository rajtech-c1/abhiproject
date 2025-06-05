package abhimanpower.app.abhihire.zAPIEndPoints

import abhimanpower.app.abhihire.loginModule.apiFunctions.LoginResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddWorkerResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetMonthlyStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetOverallStatsResponse
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
        @Part("WorkCategory") workCategory: RequestBody,
        @Part("Experience") experience: RequestBody,
        @Part("VerificationStatus") verificationStatus: RequestBody,
        @Part("ImageSelected") imageSelected: RequestBody,
        @Part("VolunteerId") volunteerId: RequestBody,
        @Part productImage: MultipartBody.Part
    ): AddWorkerResponse





}