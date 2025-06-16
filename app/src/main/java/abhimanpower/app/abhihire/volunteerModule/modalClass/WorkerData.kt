package abhimanpower.app.abhihire.volunteerModule.modalClass


data class WorkerData(
    val name: String = "",
    val mobileno: String = "",
    var dob:String = "",
    var gender: Int = 0,
    var street: String = "",
    var area: String = "",
    var pincode: String = "",
    var state: Int = 0,
    var district: Int = 0,
    var workcategory: Int = 0,
    var experience: String = "",
    var verificationstatus: Int = 0,
    var volunteerId: Int = 0,
    var imageSelected:Int = 0,
    var profileImageUrl : String = "",
    var workerId :String = ""
)