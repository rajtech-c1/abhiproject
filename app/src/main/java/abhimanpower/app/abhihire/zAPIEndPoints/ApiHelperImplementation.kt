package abhimanpower.app.abhihire.zAPIEndPoints

import abhimanpower.app.abhihire.contractorModule.modalClass.AddWorkDataResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.ContractorLoginResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.GetAvailableWorkersResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.WorkData
import abhimanpower.app.abhihire.loginModule.apiFunctions.LoginResponse
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.loginModule.modalClass.WorkerLoginResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddContractorResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddWorkerResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.ContractorData
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetMonthlyStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetOverallStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkerData
import abhimanpower.app.abhihire.workerModule.modalClass.GetAvailableWorksResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ApiHelperImplementation @Inject constructor(private val apiService: ApiInterface) :
    ApiHelper {

    override suspend fun validateLogin(): LoginResponse {
        return apiService.validateLogin(
            LoginCredentials.userName, LoginCredentials.password
        )
    }

    override suspend fun workerLogin(mobileNo: String): WorkerLoginResponse {
        return apiService.workerLogin(mobileNo)
    }

    override suspend fun contractorLogin(mobileNo: String): ContractorLoginResponse {
        return apiService.contractorLogin(mobileNo)
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
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.state.toString()),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.district.toString()),
            RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                workerData.workcategory.toString()
            ),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.experience),
            RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                workerData.verificationstatus.toString()
            ),
            RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                workerData.imageSelected.toString()
            ),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.volunteerId.toString()),
            photo
        )
    }

    override suspend fun addContractor(
        contractorData: ContractorData,
        photo: MultipartBody.Part
    ): AddContractorResponse {
        return apiService.addContractor(
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.shopName),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.mobileno),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.emailId),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.street),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.area),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.pincode),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.district),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.state),
            RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                contractorData.verificationstatus.toString()
            ),
            RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                contractorData.imageSelected.toString()
            ),
            RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                contractorData.volunteerId.toString()
            ),
            photo
        )
    }

    override suspend fun updateWorkerProfile(
        workerData: WorkerData,
        photo: MultipartBody.Part
    ): AddWorkerResponse {
        return apiService.updateWorkerProfile(
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.name),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.mobileno),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.dob),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.gender.toString()),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.street),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.area),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.pincode),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.state.toString()),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.district.toString()),
            RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                workerData.workcategory.toString()
            ),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.experience),
            RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                workerData.verificationstatus.toString()
            ),
            RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                workerData.imageSelected.toString()
            ),
            RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                workerData.profileImageUrl.toString()
            ),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.volunteerId.toString()),
            RequestBody.create("text/plain".toMediaTypeOrNull(), workerData.workerId.toString()),
            photo
        )
    }

    override suspend fun updateContractorProfile(
        contractorData: ContractorData,
        photo: MultipartBody.Part
    ): AddContractorResponse {
        return apiService.updateContractorProfile(
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.contractorId),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.shopName),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.mobileno),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.emailId),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.street),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.area),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.pincode),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.state),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.district),
            RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                contractorData.verificationstatus.toString()
            ),
            RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                contractorData.imageSelected.toString()
            ),
            RequestBody.create("text/plain".toMediaTypeOrNull(), contractorData.profileImageUrl),
            RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                contractorData.volunteerId.toString()
            ),
            photo
        )
    }

    override suspend fun getAvailableWorks(): GetAvailableWorksResponse {

        if (LoginCredentials.selectedRole == 3 || LoginCredentials.selectedRole == 4)
            return apiService.getPostedWorks(LoginCredentials.contractorAccountData.sno)

        return apiService.getAvailableWorks()
    }

    override suspend fun deletePost(postId: Int): GetAvailableWorksResponse {
        return apiService.deletePost(postId)
    }

    override suspend fun getAvailableWorkers(workCategory: Int): GetAvailableWorkersResponse {

        return apiService.getAvailableWorkers(workCategory)
    }

    override suspend fun addWorkData(workData: WorkData): AddWorkDataResponse {
        return apiService.addWorkData(
            workData.name,
            workData.description,
            workData.area,
            workData.pincode,
            workData.workcategory,
            workData.state,
            workData.district,
            workData.userId
        )
    }
}