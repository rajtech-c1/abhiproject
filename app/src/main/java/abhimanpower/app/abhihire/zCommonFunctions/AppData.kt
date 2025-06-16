package abhimanpower.app.abhihire.zCommonFunctions

import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkCategory
import abhimanpower.app.abhihire.workerModule.modalClass.Area
import android.content.Context

object AppData {
    val workCategories: List<WorkCategory> = listOf(
        WorkCategory(1, "Design"),
        WorkCategory(2, "Development"),
        WorkCategory(3, "Testing"),
        WorkCategory(4, "Deployment"),
        WorkCategory(5, "Documentation"),
        WorkCategory(6, "Client Meeting"),
        WorkCategory(7, "Bug Fixing"),
        WorkCategory(8, "Code Review"),
        WorkCategory(9, "Research"),
        WorkCategory(10, "Maintenance")
    )

    val areaList: List<Area> = listOf(
        Area(1, "Srikakulam"),
        Area(2, "Vizianagaram"),
        Area(3, "Visakhapatnam"),
        Area(4, "Anakapalli"),
        Area(5, "Alluri Sitarama Raju"),
        Area(6, "Parvathipuram Manyam"),
        Area(7, "Kakinada"),
        Area(8, "Dr. B.R. Ambedkar Konaseema"),
        Area(9, "East Godavari"),
        Area(10, "West Godavari"),
        Area(11, "Eluru"),
        Area(12, "NTR"),
        Area(13, "Krishna"),
        Area(14, "Guntur"),
        Area(15, "Palnadu"),
        Area(16, "Bapatla"),
        Area(17, "Prakasam"),
        Area(18, "SPS Nellore"),
        Area(19, "Kurnool"),
        Area(20, "Nandyal"),
        Area(21, "Anantapur"),
        Area(22, "Sri Sathya Sai"),
        Area(23, "YSR Kadapa"),
        Area(24, "Annamayya"),
        Area(25, "Chittoor"),
        Area(26, "Tirupati")
    )

    //Shared Preferences

    private const val PREFS_NAME = "ABHIHIRE"
    private const val loginStatusKey = "LOGIN_STATUS"

    private const val userRole = "USER_ROLE"

    private const val userMobileNo = "USER_MOBILE_NO"


    fun putUserLoginStatus(context: Context, value: Boolean) {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(loginStatusKey, value).apply()
    }

    fun getUserLoginStatus(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(loginStatusKey, false)
    }

    fun putUserRole(context: Context, value: Int) {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(userRole, value).apply()
    }

    fun getUserRole(context: Context): Int {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getInt(userRole, 0)
    }

    fun putUserMobileNo(context: Context, value: String) {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(userMobileNo, value).apply()
    }

    fun getUserMobileNo(context: Context): String {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(userMobileNo, "EMPTY")?:"EMPTY"
    }

}