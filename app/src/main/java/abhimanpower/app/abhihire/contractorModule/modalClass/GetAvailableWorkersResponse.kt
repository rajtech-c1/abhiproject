package abhimanpower.app.abhihire.contractorModule.modalClass

import abhimanpower.app.abhihire.workerModule.modalClass.WorkData
import abhimanpower.app.abhihire.workerModule.modalClass.WorkerAccountData
import com.google.gson.annotations.SerializedName



data class GetAvailableWorkersResponse(
    @SerializedName("status")
    val status: Int = 195,
    @SerializedName("data")
    val workersList: ArrayList<WorkerAccountData> = ArrayList()
)