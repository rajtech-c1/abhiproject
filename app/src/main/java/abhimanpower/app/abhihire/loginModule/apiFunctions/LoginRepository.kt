package abhimanpower.app.abhihire.loginModule.apiFunctions

import abhimanpower.app.abhihire.contractorModule.modalClass.ContractorLoginResponse
import abhimanpower.app.abhihire.loginModule.modalClass.WorkerLoginResponse
import abhimanpower.app.abhihire.zAPIEndPoints.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    private var loginResponse = LoginResponse()

    suspend fun validateLogin(): LoginResponse {
        try {
            withContext(Dispatchers.IO) {
                loginResponse = apiHelper.validateLogin()
            }
        } catch (_: Exception) {
        }
        return loginResponse
    }

    private var workerLoginResponse = WorkerLoginResponse()

    suspend fun workerLogin(mobileNo: String): WorkerLoginResponse {
        try {
            withContext(Dispatchers.IO) {
                workerLoginResponse = apiHelper.workerLogin(mobileNo)
            }
        } catch (_: Exception) {
        }
        return workerLoginResponse
    }

    private var contractorLoginResponse = ContractorLoginResponse()

    suspend fun contractorLogin(mobileNo: String): ContractorLoginResponse {
        try {
            withContext(Dispatchers.IO) {
                contractorLoginResponse = apiHelper.contractorLogin(mobileNo)
            }
        } catch (_: Exception) {
        }
        return contractorLoginResponse
    }

}