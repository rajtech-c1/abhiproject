package abhimanpower.app.abhihire.volunteerModule.apiFunctions

import abhimanpower.app.abhihire.loginModule.apiFunctions.LoginResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddWorkerResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetMonthlyStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetOverallStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkerData
import abhimanpower.app.abhihire.zAPIEndPoints.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject



class VoulnteerRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {

    private var getOverallStatsResponse = GetOverallStatsResponse()

    suspend fun getOverallStats(volunteerId: Int): GetOverallStatsResponse {
        try {
            withContext(Dispatchers.IO) {
                getOverallStatsResponse = apiHelper.getOverallStats(volunteerId)
            }
        } catch (_: Exception) {
        }
        return getOverallStatsResponse
    }

    private var getMonthlyStatsResponse = GetMonthlyStatsResponse()

    suspend fun getMonthlyStats(month: String,volunteerId: Int): GetMonthlyStatsResponse {
        try {
            withContext(Dispatchers.IO) {
                getMonthlyStatsResponse = apiHelper.getMonthlyStats(month, volunteerId)
            }
        } catch (_: Exception) {
        }
        return getMonthlyStatsResponse
    }

    private var addWorkerResponse = AddWorkerResponse()

    suspend fun addWorker(workerData: WorkerData,photo: MultipartBody.Part): AddWorkerResponse {
        try {
            withContext(Dispatchers.IO) {
                addWorkerResponse = apiHelper.addWorker(workerData,photo)
            }
        } catch (_: Exception) {
        }
        return addWorkerResponse
    }

}