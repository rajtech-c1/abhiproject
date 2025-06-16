package abhimanpower.app.abhihire.volunteerModule.modalClass

import com.google.gson.annotations.SerializedName



data class VolunteerAccountData(
    @SerializedName("VolunteerId")
    val volunteerId: String = "",

    @SerializedName("Name")
    val name: String = "",

    @SerializedName("MobileNo")
    val mobileNo: String = "",


    )