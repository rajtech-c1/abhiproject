package abhimanpower.app.abhihire.zCommonFunctions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.Locale


object UtilFunctions {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

     fun formatDateToMonthDay(inputDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM d", Locale.getDefault()) // e.g., May 1
            val date = inputFormat.parse(inputDate)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            ""
        }
    }

    fun openDialer(context: Context, phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Handle case where no dialer app is available
            Toast.makeText(context, "No dialer app found", Toast.LENGTH_SHORT).show()
        }
    }

    var isLanguageSelected = false

    fun showLanguageSelectionDialog(context: Context, activity: Activity) {
        val languages = arrayOf("English", "Telugu")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select Language")
        builder.setSingleChoiceItems(languages, -1) { dialog, which ->
            when (which) {
                0 -> setLocale("en", context, activity) // English
                1 -> setLocale("te", context, activity) // Telugu
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel", null)
        val dialog = builder.create()
        dialog.show()
    }

    fun setLocale(languageCode: String, context: Context, activity: Activity) {
        saveUserLanguage(context, languageCode)
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        activity.recreate() // Reload activity to apply changes
    }

    fun loadLanguage(context: Context, activity: Activity) {
        val languageCode =
            getUserLanguage(context) // Default to English if no language is saved
//        setLocale(languageCode, context, activity)
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }


    fun saveUserLanguage(context: Context, value: String) {
        val sharedPref = context.getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("USER_LANGUAGE", value).apply()
    }

    fun getUserLanguage(context: Context): String {
        val sharedPref = context.getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE)
        return sharedPref.getString("USER_LANGUAGE", "en")?:"en"
    }

}