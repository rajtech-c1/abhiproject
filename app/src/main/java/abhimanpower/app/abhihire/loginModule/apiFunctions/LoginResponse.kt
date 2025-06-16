package abhimanpower.app.abhihire.loginModule.apiFunctions

import abhimanpower.app.abhihire.volunteerModule.modalClass.VolunteerAccountData
import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val accountData: VolunteerAccountData = VolunteerAccountData()
)