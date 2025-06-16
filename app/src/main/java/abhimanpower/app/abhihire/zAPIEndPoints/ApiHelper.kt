package abhimanpower.app.abhihire.zAPIEndPoints

import abhimanpower.app.abhihire.contractorModule.modalClass.AddWorkDataResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.ContractorLoginResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.GetAvailableWorkersResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.WorkData
import abhimanpower.app.abhihire.loginModule.apiFunctions.LoginResponse
import abhimanpower.app.abhihire.loginModule.modalClass.WorkerLoginResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddContractorResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddWorkerResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.ContractorData
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetMonthlyStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetOverallStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkerData
import abhimanpower.app.abhihire.workerModule.modalClass.GetAvailableWorksResponse
import okhttp3.MultipartBody

interface ApiHelper {
    suspend fun validateLogin(): LoginResponse

    suspend fun workerLogin(mobileNo: String): WorkerLoginResponse

    suspend fun contractorLogin(mobileNo: String): ContractorLoginResponse

    suspend fun getOverallStats(volunteerId: Int): GetOverallStatsResponse

    suspend fun getMonthlyStats(month: String,volunteerId: Int): GetMonthlyStatsResponse

    suspend fun addWorker(workerData: WorkerData,photo: MultipartBody.Part): AddWorkerResponse

    suspend fun addContractor(contractorData: ContractorData,photo: MultipartBody.Part): AddContractorResponse

    suspend fun updateWorkerProfile(workerData: WorkerData,photo: MultipartBody.Part): AddWorkerResponse

    suspend fun getAvailableWorks(): GetAvailableWorksResponse

    suspend fun getAvailableWorkers(workCategory: Int): GetAvailableWorkersResponse

    suspend fun addWorkData(workData: WorkData): AddWorkDataResponse



}