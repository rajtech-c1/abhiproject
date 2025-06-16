package abhimanpower.app.abhihire.contractorModule.modalClass

import com.google.gson.annotations.SerializedName


data class ContractorLoginResponse(
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val accountData: ContractorAccountData = ContractorAccountData()
)