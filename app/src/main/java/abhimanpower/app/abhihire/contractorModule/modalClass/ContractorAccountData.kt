package abhimanpower.app.abhihire.contractorModule.modalClass

import com.google.gson.annotations.SerializedName


data class ContractorAccountData(
    @SerializedName("SNo")
    val sno: String = "",

    @SerializedName("Name")
    val name: String = "",

    @SerializedName("MobileNo")
    val mobileNo: String = "",

    @SerializedName("Email")
    val email: String = "",


    @SerializedName("Street")
    val street: String = "",

    @SerializedName("Area")
    val area: String = "",

    @SerializedName("Pincode")
    val pincode: String = "",

    @SerializedName("State")
    val state: String = "",

    @SerializedName("VerificationStatus")
    val verificationStatus: String = "",

    @SerializedName("ContractorId")
    val contractorId: String = "",

    @SerializedName("Image")
    val image: String = "",

    @SerializedName("AddedDate")
    val addedDate: String = "",

    @SerializedName("VolunteerId")
    val volunteerId: String = ""
)
