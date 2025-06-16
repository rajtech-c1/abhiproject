package abhimanpower.app.abhihire.volunteerModule.modalClass

import abhimanpower.app.abhihire.contractorModule.modalClass.ContractorAccountData
import com.google.gson.annotations.SerializedName


data class AddContractorResponse(
    @SerializedName("status")
    val status: Int = 195,
    @SerializedName("message")
    val message: String = "EMPTY",
    @SerializedName("data")
    val accountData: ContractorAccountData = ContractorAccountData()
)