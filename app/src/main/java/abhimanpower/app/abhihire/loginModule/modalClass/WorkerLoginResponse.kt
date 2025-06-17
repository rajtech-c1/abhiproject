package abhimanpower.app.abhihire.loginModule.modalClass

import abhimanpower.app.abhihire.workerModule.modalClass.PlanData
import abhimanpower.app.abhihire.workerModule.modalClass.WorkerAccountData
import com.google.gson.annotations.SerializedName


data class WorkerLoginResponse(
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("worker")
    val accountData: WorkerAccountData = WorkerAccountData(),
    @SerializedName("plan")
    val plan: PlanData = PlanData()
)