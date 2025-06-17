package abhimanpower.app.abhihire.workerModule.modalClass

import com.google.gson.annotations.SerializedName

class PlanData (
    @SerializedName("PlanId")
    val planId: String = "",
    @SerializedName("PlanName")
    val planName: String = "",
    @SerializedName("Price")
    val price: String = ""
)