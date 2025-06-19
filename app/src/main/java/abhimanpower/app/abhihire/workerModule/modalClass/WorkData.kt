package abhimanpower.app.abhihire.workerModule.modalClass

import com.google.gson.annotations.SerializedName


data class WorkData(
    @SerializedName("WorkId")
    val workId: String = "0",
    @SerializedName("Name")
    val workName: String = "",
    @SerializedName("Place")
    val place: String = "",
    @SerializedName("State")
    val state: String = "",
    @SerializedName("District")
    val district: String = "",
    @SerializedName("Description")
    val description: String = "",
    @SerializedName("WorkCategory")
    val workCategory: String = "0",
    @SerializedName("Pincode")
    val pincode: String = "",
    @SerializedName("DatePosted")
    val datePosted: String = "",

    @SerializedName("UserId")
    val userId: String = "0",
    @SerializedName("UserName")
    val userName: String = "0",
    @SerializedName("UserImage")
    val userImage: String = "0",
    @SerializedName("MobileNo")
    val userContact: String = "0",
    @SerializedName("UserDistrict")
    val userDistrict: String = "0",
    @SerializedName("UserState")
    val userState: String = "0",
    @SerializedName("Image")
    val image: String = "0",
    @SerializedName("UserType")
    val userType: String = "0",

    )