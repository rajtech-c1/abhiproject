package abhimanpower.app.abhihire.zCommonFunctions

import abhimanpower.app.abhihire.workerModule.modalClass.District
import abhimanpower.app.abhihire.workerModule.modalClass.State

object AreaData {

    fun getDistrict(districtId: Int, stateId: Int): String {
        var districtState = "D,S"
        when (stateId) {
            1 -> {
                val district = telanganaDistricts.find { it.districtId == districtId }

                if (district != null)
                    districtState = district.districtName
            }

            2 -> {
                val district = andhraPradeshDistricts.find { it.districtId == districtId }

                if (district != null)
                    districtState = district.districtName
            }

        }

        return districtState
    }

    fun getState(stateId: Int): String {
        val state = states[stateId].stateName
        return state
    }

    fun getDistrictState(districtId: Int, stateId: Int): String {
        var districtState = "D,S"
        when (stateId) {
            1 -> {
                val district = telanganaDistricts.find { it.districtId == districtId }
                val state = states[0]

                if (district != null)
                    districtState = district.districtName + "," + state.stateName
            }

            2 -> {
                val district = andhraPradeshDistricts.find { it.districtId == districtId }
                val state = states[1]

                if (district != null)
                    districtState = district.districtName + "," + state.stateName
            }

        }

        return districtState
    }

    val states = listOf(
        State(1, "Telangana"),
        State(2, "Andhra Pradesh")
    )

    val telanganaDistricts = listOf(
        District(1, "Adilabad", 1),
        District(2, "Bhadradri Kothagudem", 1),
        District(3, "Hanamkonda", 1),
        District(4, "Hyderabad", 1),
        District(5, "Jagtial", 1),
        District(6, "Jangaon", 1),
        District(7, "Jayashankar Bhupalpally", 1),
        District(8, "Jogulamba Gadwal", 1),
        District(9, "Kamareddy", 1),
        District(10, "Karimnagar", 1),
        District(11, "Khammam", 1),
        District(12, "Kumuram Bheem", 1),
        District(13, "Mahabubabad", 1),
        District(14, "Mahbubnagar", 1),
        District(15, "Mancherial", 1),
        District(16, "Medak", 1),
        District(17, "Medchal–Malkajgiri", 1),
        District(18, "Mulugu", 1),
        District(19, "Nagarkurnool", 1),
        District(20, "Nalgonda", 1),
        District(21, "Narayanpet", 1),
        District(22, "Nirmal", 1),
        District(23, "Nizamabad", 1),
        District(24, "Peddapalli", 1),
        District(25, "Rajanna Sircilla", 1),
        District(26, "Ranga Reddy", 1),
        District(27, "Sangareddy", 1),
        District(28, "Siddipet", 1),
        District(29, "Suryapet", 1),
        District(30, "Vikarabad", 1),
        District(31, "Wanaparthy", 1),
        District(32, "Warangal", 1),
        District(33, "Yadadri Bhuvanagiri", 1)
    )

    // Andhra Pradesh Districts (stateId = 2)
    val andhraPradeshDistricts = listOf(
        District(101, "Alluri Sitharama Raju", 2),
        District(102, "Anakapalli", 2),
        District(103, "Ananthapuramu", 2),
        District(104, "Annamayya", 2),
        District(105, "Bapatla", 2),
        District(106, "Chittoor", 2),
        District(107, "Dr. B. R. Ambedkar Konaseema", 2),
        District(108, "East Godavari", 2),
        District(109, "Eluru", 2),
        District(110, "Guntur", 2),
        District(111, "Kakinada", 2),
        District(112, "Krishna", 2),
        District(113, "Kurnool", 2),
        District(114, "Nandyal", 2),
        District(115, "Sri Potti Sriramulu Nellore", 2),
        District(116, "NTR", 2),
        District(117, "Palnadu", 2),
        District(118, "Parvathipuram Manyam", 2),
        District(119, "Prakasam", 2),
        District(120, "Srikakulam", 2),
        District(121, "Sri Sathya Sai", 2),
        District(122, "Tirupati", 2),
        District(123, "Visakhapatnam", 2),
        District(124, "Vizianagaram", 2),
        District(125, "West Godavari", 2),
        District(126, "YSR Kadapa", 2)
    )
}