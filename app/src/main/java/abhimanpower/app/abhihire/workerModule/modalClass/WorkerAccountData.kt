package abhimanpower.app.abhihire.workerModule.modalClass
import com.google.gson.annotations.SerializedName

data class WorkerAccountData(
    @SerializedName("SNo")
    val sno: String = "",

    @SerializedName("Name")
    val name: String = "",

    @SerializedName("MobileNo")
    val mobileNo: String = "",

    @SerializedName("DOB")
    val dob: String = "",

    @SerializedName("Gender")
    val gender: String = "",

    @SerializedName("Street")
    val street: String = "",

    @SerializedName("Area")
    val area: String = "",

    @SerializedName("Pincode")
    val pincode: String = "",

    @SerializedName("District")
    val district: String = "",

    @SerializedName("State")
    val state: String = "",

    @SerializedName("WorkCategory")
    val workCategory: String = "",

    @SerializedName("Experience")
    val experience: String = "",

    @SerializedName("VerificationStatus")
    val verificationStatus: String = "",

    @SerializedName("SubscriptionEndingDate")
    val subscriptionEndingDate: String = "",


    @SerializedName("WorkerId")
    val workerId: String = "",

    @SerializedName("Image")
    val image: String = "",

    @SerializedName("AddedDate")
    val addedDate: String = "",

    @SerializedName("VolunteerId")
    val volunteerId: String = "",

)
