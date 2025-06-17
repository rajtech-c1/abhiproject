package abhimanpower.app.abhihire.volunteerModule.modalClass

import com.google.gson.annotations.SerializedName



data class WorkersStats(
    @SerializedName("total")
    val totalCount: Int? = null,
    @SerializedName("today")
    val todayCount: Int? = null,
    @SerializedName("week")
    val weekCount: Int? = null,
)