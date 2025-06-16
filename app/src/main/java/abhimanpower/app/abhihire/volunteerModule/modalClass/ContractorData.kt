package abhimanpower.app.abhihire.volunteerModule.modalClass


data class ContractorData(
    val shopName: String = "",
    val mobileno: String = "",
    var emailId: String = "",
    var street: String = "",
    var area: String = "",
    var pincode: String = "",
    var district: String = "2",
    var state: String = "",
    var verificationstatus: Int = 0,
    var volunteerId: Int = 0,
    var imageSelected:Int = 0,
    var profileImageUrl : String = "",
    var contractorId :String = ""
)