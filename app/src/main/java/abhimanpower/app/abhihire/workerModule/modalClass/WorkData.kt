package abhimanpower.app.abhihire.workerModule.modalClass

import com.google.gson.annotations.SerializedName


data class WorkData(
    @SerializedName("count")
    val count: Int = 195,
    @SerializedName("WorkName")
    val workName: String = "",
    @SerializedName("Place")
    val place: String = "",
    @SerializedName("Description")
    val description: String = "",
)