package abhimanpower.app.abhihire.zCommonFunctions

import android.content.Context
import android.widget.Toast


object UtilFunctions {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}