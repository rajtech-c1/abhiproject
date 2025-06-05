package abhimanpower.app.abhihire.volunteerModule.modalClass

import com.google.gson.annotations.SerializedName



data class GetMonthlyStatsResponse(
    @SerializedName("status")
    val status: Int = 195,
    @SerializedName("total_count")
    val totalCount: Int = 10,
    @SerializedName("data")
    val daysList: ArrayList<DayData> = ArrayList()
)