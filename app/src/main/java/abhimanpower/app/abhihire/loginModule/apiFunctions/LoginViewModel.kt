package abhimanpower.app.abhihire.loginModule.apiFunctions

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
}