package abhimanpower.app.abhihire.volunteerModule.modalClass

import com.google.gson.annotations.SerializedName



data class AddWorkerResponse(
    @SerializedName("status")
    val status: Int = 195,
    @SerializedName("message")
    val message: String = "EMPTY"
)