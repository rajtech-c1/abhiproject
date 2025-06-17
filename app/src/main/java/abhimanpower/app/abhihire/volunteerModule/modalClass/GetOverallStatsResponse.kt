package abhimanpower.app.abhihire.volunteerModule.modalClass

import com.google.gson.annotations.SerializedName



data class GetOverallStatsResponse(
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("workers")
    val workers: WorkersStats? = null,
    @SerializedName("contractors")
    val contractors: WorkersStats? = null
)