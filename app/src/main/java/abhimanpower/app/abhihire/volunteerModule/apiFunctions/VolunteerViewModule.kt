package abhimanpower.app.abhihire.volunteerModule.apiFunctions

import abhimanpower.app.abhihire.volunteerModule.modalClass.AddContractorResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddWorkerResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.ContractorData
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetMonthlyStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetOverallStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkerData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject


@HiltViewModel
class VolunteerViewModule @Inject constructor(
    private val volunteerRepository: VoulnteerRepository
) : ViewModel() {

    private var getOverallStatsResponse = MutableLiveData<GetOverallStatsResponse>()

    fun getOverallStats(volunteerId: Int) {
        viewModelScope.launch {
            getOverallStatsResponse.postValue(volunteerRepository.getOverallStats(volunteerId))
        }
    }

    fun resetGetOverallStatsResponse() {
        getOverallStatsResponse = MutableLiveData<GetOverallStatsResponse>()
    }

    fun getOverallStatsResponse(): LiveData<GetOverallStatsResponse> {
        return getOverallStatsResponse
    }

    private var getMonthlyStatsResponse = MutableLiveData<GetMonthlyStatsResponse>()

    fun getMonthlyStats(month: String, volunteerId: Int) {
        viewModelScope.launch {
            getMonthlyStatsResponse.postValue(
                volunteerRepository.getMonthlyStats(
                    month,
                    volunteerId
                )
            )
        }
    }

    fun resetGetMonthlyStatsResponse() {
        getMonthlyStatsResponse = MutableLiveData<GetMonthlyStatsResponse>()
    }

    fun getMonthlyStatsResponse(): LiveData<GetMonthlyStatsResponse> {
        return getMonthlyStatsResponse
    }

    private var addWorkerResponse = MutableLiveData<AddWorkerResponse>()

    fun addWorker(workerData: WorkerData, photo: MultipartBody.Part) {
        viewModelScope.launch {
            addWorkerResponse.postValue(volunteerRepository.addWorker(workerData, photo))
        }
    }

    fun resetAddWorkerResponse() {
        addWorkerResponse = MutableLiveData<AddWorkerResponse>()
    }

    fun getAddWorkerResponse(): LiveData<AddWorkerResponse> {
        return addWorkerResponse
    }

    private var addContractorResponse = MutableLiveData<AddContractorResponse>()

    fun addContractor(contractorData: ContractorData, photo: MultipartBody.Part) {
        viewModelScope.launch {
            addContractorResponse.postValue(
                volunteerRepository.addContractor(
                    contractorData,
                    photo
                )
            )
        }
    }

    fun resetAddContractorResponse() {
        addContractorResponse = MutableLiveData<AddContractorResponse>()
    }

    fun getAddContractorResponse(): LiveData<AddContractorResponse> {
        return addContractorResponse
    }
}