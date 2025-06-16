package abhimanpower.app.abhihire.workerModule.modalClass

import abhimanpower.app.abhihire.volunteerModule.modalClass.DayData
import com.google.gson.annotations.SerializedName



data class GetAvailableWorksResponse(
    @SerializedName("status")
    val status: Int = 195,
    @SerializedName("data")
    val worksList: ArrayList<WorkData> = ArrayList()
)