package abhimanpower.app.abhihire.zAPIEndPoints

import abhimanpower.app.abhihire.loginModule.apiFunctions.LoginResponse
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddWorkerResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetMonthlyStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetOverallStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkerData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ApiHelperImplementation @Inject constructor(private val apiService: ApiInterface) :
    ApiHelper {

    override suspend fun validateLogin(): LoginResponse {
        return apiService.validateLogin(
            LoginCredentials.userName
        )
    }

    override suspend fun getOverallStats(volunteerId: Int): GetOverallStatsResponse {
        return apiService.getOverallStats(volunteerId)
    }

    override suspend fun getMonthlyStats(month: String, volunteerId: Int): GetMonthlyStatsResponse {
        return apiService.getMonthlyStats(month, volunteerId)
    }

    override suspend fun addWorker(
        workerData: WorkerData,
        photo: MultipartBody.Part
    ): AddWorkerResponse {
        return apiService.addWorker(
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.name),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.mobileno),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.dob),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.gender.toString()),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.street),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.area),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.pincode),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.state),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.workcategory.toString()),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.experience),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.verificationstatus.toString()),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.imageSelected.toString()),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.volunteerId.toString()),
            photo
        )
    }
}