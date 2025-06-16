package abhimanpower.app.abhihire.workerModule.apiFunctions

import abhimanpower.app.abhihire.volunteerModule.modalClass.AddWorkerResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetOverallStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.VolunteerData.volunteerId
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkerData
import abhimanpower.app.abhihire.workerModule.modalClass.GetAvailableWorksResponse
import abhimanpower.app.abhihire.zAPIEndPoints.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject



class WorkerRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {

    private var getAvailableWorksResponse = GetAvailableWorksResponse()

    suspend fun getAvailableWorks(): GetAvailableWorksResponse {
        try {
            withContext(Dispatchers.IO) {
                getAvailableWorksResponse = apiHelper.getAvailableWorks()
            }
        } catch (_: Exception) {
        }
        return getAvailableWorksResponse
    }

    private var updateWorkerResponse = AddWorkerResponse()

    suspend fun updateWorkerProfile(workerData: WorkerData, photo: MultipartBody.Part): AddWorkerResponse {
        try {
            withContext(Dispatchers.IO) {
                updateWorkerResponse = apiHelper.updateWorkerProfile(workerData,photo)
            }
        } catch (_: Exception) {
        }
        return updateWorkerResponse
    }

}