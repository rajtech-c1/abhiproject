package abhimanpower.app.abhihire.contractorModule.modalClass


data class WorkData(
    val name: String = "",
    val description: String = "",
    var area: String = "",
    var pincode: String = "",
    var state: Int = 0,
    var district: Int = 0,
    var workcategory: Int = 0,
    var userId: Int = 0
)