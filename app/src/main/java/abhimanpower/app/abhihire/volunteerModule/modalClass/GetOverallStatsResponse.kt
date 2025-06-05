package abhimanpower.app.abhihire.volunteerModule.modalClass

import com.google.gson.annotations.SerializedName



data class GetOverallStatsResponse(
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("total_count")
    val totalCount: Int? = null,
    @SerializedName("today_count")
    val todayCount: Int? = null,
    @SerializedName("week_count")
    val weekCount: Int? = null,
)