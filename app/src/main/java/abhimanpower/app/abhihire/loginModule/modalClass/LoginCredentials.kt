package abhimanpower.app.abhihire.loginModule.modalClass

import abhimanpower.app.abhihire.contractorModule.modalClass.ContractorAccountData
import abhimanpower.app.abhihire.volunteerModule.modalClass.VolunteerAccountData
import abhimanpower.app.abhihire.workerModule.modalClass.WorkerAccountData

object LoginCredentials {
    var userRole = 0
    var userName = ""
    var password = ""

    var selectedRole = 1 // 0-Volunteer, 1- Worker , 2- Public User , 3 - Shop User

    var workerAccountData = WorkerAccountData()  // 1-Worker

    var volunteerAccountData = VolunteerAccountData() // 2- Volunteer

    var contractorAccountData = ContractorAccountData() //3-Contractor
}