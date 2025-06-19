package abhimanpower.app.abhihire.workerModule.modalClass

import abhimanpower.app.abhihire.contractorModule.modalClass.ContractorAccountData
import com.google.gson.annotations.SerializedName


data class WorkClientData(
    @SerializedName("work_details")
    val workDetails: WorkData = WorkData(),
    @SerializedName("contractor_data")
    val contractorData: ContractorAccountData = ContractorAccountData()
)