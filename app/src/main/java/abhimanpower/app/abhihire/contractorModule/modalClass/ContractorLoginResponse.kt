package abhimanpower.app.abhihire.contractorModule.modalClass

import abhimanpower.app.abhihire.workerModule.modalClass.PlanData
import com.google.gson.annotations.SerializedName


data class ContractorLoginResponse(
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("contractor")
    val accountData: ContractorAccountData = ContractorAccountData(),
    @SerializedName("plan")
    val plan: PlanData = PlanData()
)