package abhimanpower.app.abhihire.zCommonFunctions

import abhimanpower.app.abhihire.volunteerModule.AddWorkerActivity
import android.app.Activity
import android.content.Context
import android.content.Intent


object CallIntent {

    fun gotoAddWorkerActivity(context: Context, killMe: Boolean, activity: Activity) {
        val intent = Intent(context, AddWorkerActivity::class.java)
        context.startActivity(intent)
        if (killMe) activity.finish()
    }

}