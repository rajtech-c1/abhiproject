package abhimanpower.app.abhihire.contractorModule.apiFunctions

import abhimanpower.app.abhihire.contractorModule.modalClass.AddWorkDataResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.GetAvailableWorkersResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.WorkData
import abhimanpower.app.abhihire.loginModule.apiFunctions.LoginRepository
import abhimanpower.app.abhihire.loginModule.apiFunctions.LoginResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContractorViewModel @Inject constructor(
    private val contractorRepository: ContractorRepository
) : ViewModel() {

    private var getAvailableWorkersResponse = MutableLiveData<GetAvailableWorkersResponse>()

    fun getAvailableWorkers(workCategory: Int) {
        viewModelScope.launch {
            getAvailableWorkersResponse.postValue(contractorRepository.getAvailableWorkers(workCategory))
        }
    }

    fun resetGetAvailableWorkersResponse() {
        getAvailableWorkersResponse = MutableLiveData<GetAvailableWorkersResponse>()
    }

    fun getAvailableWorkersResponse(): LiveData<GetAvailableWorkersResponse> {
        return getAvailableWorkersResponse
    }

    private var addWorkData = MutableLiveData<AddWorkDataResponse>()

    fun addWork(workData: WorkData) {
        viewModelScope.launch {
            addWorkData.postValue(contractorRepository.addWorkData(workData))
        }
    }

    fun resetAddWorkResponse() {
        addWorkData = MutableLiveData<AddWorkDataResponse>()
    }

    fun getAddWorkResponse(): LiveData<AddWorkDataResponse> {
        return addWorkData
    }


}