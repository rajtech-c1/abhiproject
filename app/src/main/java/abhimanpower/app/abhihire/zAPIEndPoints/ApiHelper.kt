package abhimanpower.app.abhihire.zAPIEndPoints

import abhimanpower.app.abhihire.loginModule.apiFunctions.LoginResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddWorkerResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetMonthlyStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetOverallStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkerData
import okhttp3.MultipartBody

interface ApiHelper {
    suspend fun validateLogin(): LoginResponse

    suspend fun getOverallStats(volunteerId: Int): GetOverallStatsResponse

    suspend fun getMonthlyStats(month: String,volunteerId: Int): GetMonthlyStatsResponse


    suspend fun addWorker(workerData: WorkerData,photo: MultipartBody.Part): AddWorkerResponse

}