package abhimanpower.app.abhihire.volunteerModule.modalClass


data class WorkerData(
    val name: String = "",
    val mobileno: String = "",
    var dob:String = "",
    var gender: Int = 0,
    var street: String = "",
    var area: String = "",
    var pincode: String = "",
    var state: String = "",
    var workcategory: Int = 0,
    var experience: String = "",
    var verificationstatus: Int = 0,
    var volunteerId: Int = 0,
    var imageSelected:Int = 0
)