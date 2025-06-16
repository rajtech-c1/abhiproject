package abhimanpower.app.abhihire.contractorModule.apiFunctions

import abhimanpower.app.abhihire.contractorModule.modalClass.AddWorkDataResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.GetAvailableWorkersResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.WorkData
import abhimanpower.app.abhihire.loginModule.apiFunctions.LoginResponse
import abhimanpower.app.abhihire.zAPIEndPoints.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ContractorRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    private var getAvailableWorkersResponse = GetAvailableWorkersResponse()

    suspend fun getAvailableWorkers(workCategory:Int): GetAvailableWorkersResponse {
        try {
            withContext(Dispatchers.IO) {
                getAvailableWorkersResponse = apiHelper.getAvailableWorkers(workCategory)
            }
        } catch (_: Exception) {
        }
        return getAvailableWorkersResponse
    }

    private var addWorkData = AddWorkDataResponse()

    suspend fun addWorkData(workData: WorkData): AddWorkDataResponse {
        try {
            withContext(Dispatchers.IO) {
                addWorkData = apiHelper.addWorkData(workData)
            }
        } catch (_: Exception) {
        }
        return addWorkData
    }

}