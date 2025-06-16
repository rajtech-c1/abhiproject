package abhimanpower.app.abhihire.loginModule.apiFunctions

import abhimanpower.app.abhihire.contractorModule.modalClass.ContractorLoginResponse
import abhimanpower.app.abhihire.loginModule.modalClass.WorkerLoginResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private var loginResponse = MutableLiveData<LoginResponse>()

    fun validateLogin() {
        viewModelScope.launch {
            loginResponse.postValue(loginRepository.validateLogin())
        }
    }

    fun resetLoginResponse() {
        loginResponse = MutableLiveData<LoginResponse>()
    }

    fun getLoginResponse(): LiveData<LoginResponse> {
        return loginResponse
    }

    private var workerLoginResponse = MutableLiveData<WorkerLoginResponse>()

    fun workerLogin(mobileNo: String) {
        viewModelScope.launch {
            workerLoginResponse.postValue(loginRepository.workerLogin(mobileNo))
        }
    }

    fun resetWorkerLoginResponse() {
        workerLoginResponse = MutableLiveData<WorkerLoginResponse>()
    }

    fun getWorkerLoginResponse(): LiveData<WorkerLoginResponse> {
        return workerLoginResponse
    }

    private var contractorLoginResponse = MutableLiveData<ContractorLoginResponse>()

    fun contractorLogin(mobileNo: String) {
        viewModelScope.launch {
            contractorLoginResponse.postValue(loginRepository.contractorLogin(mobileNo))
        }
    }

    fun resetContractorLoginResponse() {
        contractorLoginResponse = MutableLiveData<ContractorLoginResponse>()
    }

    fun getContractorLoginResponse(): LiveData<ContractorLoginResponse> {
        return contractorLoginResponse
    }
}