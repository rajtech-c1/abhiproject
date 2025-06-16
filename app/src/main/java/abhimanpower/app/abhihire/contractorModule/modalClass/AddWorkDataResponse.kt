package abhimanpower.app.abhihire.contractorModule.modalClass

import abhimanpower.app.abhihire.workerModule.modalClass.WorkerAccountData
import com.google.gson.annotations.SerializedName



data class AddWorkDataResponse(
    @SerializedName("status")
    val status: Int = 195,
    @SerializedName("message")
    val message: String = "EMPTY",
    @SerializedName("data")
    val accountData: WorkerAccountData = WorkerAccountData()
)