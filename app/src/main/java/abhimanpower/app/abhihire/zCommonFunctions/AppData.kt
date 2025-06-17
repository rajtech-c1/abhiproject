package abhimanpower.app.abhihire.zCommonFunctions

import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkCategory
import abhimanpower.app.abhihire.workerModule.modalClass.Area
import android.content.Context

object AppData {
    val workCategories: List<WorkCategory> = listOf(
        WorkCategory(1, "Accountant"),
        WorkCategory(2, "Air Conditioner Technician"),
        WorkCategory(3, "Agricultural Worker"),
        WorkCategory(4, "Automotive Technician"),
        WorkCategory(5, "Baker"),
        WorkCategory(6, "Barber"),
        WorkCategory(7, "Builder"),
        WorkCategory(8, "Bike Mechanic"),
        WorkCategory(9, "Beautician"),
        WorkCategory(10, "Car Mechanic"),
        WorkCategory(11, "Carpenter"),
        WorkCategory(12, "Chef/Cook"),
        WorkCategory(13, "Construction Worker"),
        WorkCategory(14, "Dental Assistant"),
        WorkCategory(15, "Doctor"),
        WorkCategory(16, "Electrician"),
        WorkCategory(17, "Engineer"),
        WorkCategory(18, "Farmer"),
        WorkCategory(19, "Firefighter"),
        WorkCategory(20, "Gardener/Landscaper"),
        WorkCategory(21, "Graphic Designer"),
        WorkCategory(22, "Hairstylist"),
        WorkCategory(23, "Healthcare Worker"),
        WorkCategory(24, "Interior Designer"),
        WorkCategory(25, "IT Specialist"),
        WorkCategory(26, "Janitor/Cleaner"),
        WorkCategory(27, "Lawyer/Attorney"),
        WorkCategory(28, "Locksmith"),
        WorkCategory(29, "Loom Weavers"),
        WorkCategory(30, "Mason"),
        WorkCategory(31, "Mechanic"),
        WorkCategory(32, "Medical Assistant"),
        WorkCategory(33, "Milk Man"),
        WorkCategory(34, "Nurse"),
        WorkCategory(35, "Painter"),
        WorkCategory(36, "Pharmacist"),
        WorkCategory(37, "Photographer"),
        WorkCategory(38, "Plumber"),
        WorkCategory(39, "Registered Medical Practitioner"),
        WorkCategory(40, "Security Guard"),
        WorkCategory(41, "Social Worker"),
        WorkCategory(42, "Tailor"),
        WorkCategory(43, "Tea Master"),
        WorkCategory(44, "Tiles/Marble Mistri"),
        WorkCategory(45, "TV/Mobile Repair Technician"),
        WorkCategory(46, "Vegetable And Fruit Merchant"),
        WorkCategory(47, "Waiter/Servor"),
        WorkCategory(48, "Welder"),
        WorkCategory(49, "Writer/Author"),
        WorkCategory(50, "Other Worker Not Specified")
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