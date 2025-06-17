package abhimanpower.app.abhihire.zCommonFunctions

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.contractorModule.AddWorkActivity
import abhimanpower.app.abhihire.contractorModule.ContractorHomeActivity
import abhimanpower.app.abhihire.contractorModule.MyWorksActivity
import abhimanpower.app.abhihire.loginModule.RoleSelectionActivity
import abhimanpower.app.abhihire.loginModule.VolunteerLoginActivity
import abhimanpower.app.abhihire.volunteerModule.AddContractorActivity
import abhimanpower.app.abhihire.volunteerModule.AddWorkerActivity
import abhimanpower.app.abhihire.volunteerModule.VolunteerHomeActivity
import abhimanpower.app.abhihire.workerModule.EditWorkerProfileActivity
import abhimanpower.app.abhihire.workerModule.RegisterWorkerActivity
import abhimanpower.app.abhihire.workerModule.WorkerDetailsActivity
import abhimanpower.app.abhihire.workerModule.WorkerHomeActivity
import abhimanpower.app.abhihire.workerModule.WorkerProfileActivity
import android.app.Activity
import android.content.Context
import android.content.Intent


object CallIntent {

    fun gotoRoleSelectionActivity(context: Context, killMe: Boolean, activity: Activity) {
        val intent = Intent(context, RoleSelectionActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(intent)
        if (killMe) activity.finish()
    }

    fun gotoLoginActivity(context: Context, killMe: Boolean, activity: Activity) {
        val intent = Intent(context, VolunteerLoginActivity::class.java).apply {
            // Clear the entire back stack and create a new task
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(intent)
        if (killMe) activity.finish()
    }

    fun gotoWorkerHomeActivity(context: Context, killMe: Boolean, activity: Activity) {
        val intent = Intent(context, WorkerHomeActivity::class.java)
        context.startActivity(intent)
        if (killMe) activity.finish()
    }

    fun gotoVolunteerHomeActivity(context: Context, killMe: Boolean, activity: Activity) {
        val intent = Intent(context, VolunteerHomeActivity::class.java)
        context.startActivity(intent)
        if (killMe) activity.finish()
    }

    fun gotoWorkerRegisterActivity(context: Context, killMe: Boolean, activity: Activity) {
        val intent = Intent(context,RegisterWorkerActivity::class.java)
        context.startActivity(intent)
        if (killMe) activity.finish()
    }

    fun gotoAddWorkerActivity(context: Context, killMe: Boolean, activity: Activity) {
        val intent = Intent(context, AddWorkerActivity::class.java)
        context.startActivity(intent)
        if (killMe) activity.finish()
    }

    fun gotoAddContractorActivity(context: Context, killMe: Boolean, activity: Activity) {
        val intent = Intent(context, AddContractorActivity::class.java)
        context.startActivity(intent)
        if (killMe) activity.finish()
    }

    fun gotoWorkDetailsActivity(context: Context, killMe: Boolean, activity: Activity) {
        val intent = Intent(context, WorkerDetailsActivity::class.java)
        context.startActivity(intent)
        if (killMe) activity.finish()
    }

    fun gotoWorkerProfileActivity(context: Context, killMe: Boolean, activity: Activity) {
        val intent = Intent(context, WorkerProfileActivity::class.java)
        context.startActivity(intent)
        activity.overridePendingTransition(R.anim.fade_in_activity, android.R.anim.fade_out) // Fade-in + fade-out
        if (killMe) activity.finish()
    }

    fun gotoEditProfileActivity(context: Context, killMe: Boolean, activity: Activity) {
        val intent = Intent(context, EditWorkerProfileActivity::class.java)
        context.startActivity(intent)
        if (killMe) activity.finish()
    }

    fun gotoMyWorksActivity(context: Context, killMe: Boolean, activity: Activity) {
        val intent = Intent(context, MyWorksActivity::class.java)
        context.startActivity(intent)
        if (killMe) activity.finish()
    }

    fun gotoAddWorkActivity(context: Context, killMe: Boolean, activity: Activity) {
        val intent = Intent(context, AddWorkActivity::class.java)
        context.startActivity(intent)
        if (killMe) activity.finish()
    }

    fun gotoContractorHomeActivity(context: Context, killMe: Boolean, activity: Activity) {
        val intent = Intent(context, AddWorkActivity::class.java)
        context.startActivity(intent)
        if (killMe) activity.finish()
    }



//    fun <T : Activity> gotoNextActivity(
//        context: Context,
//        killMe: Boolean,
//        activity: Activity,
//        targetClass: Class<T>
//    )

    fun gotoNextActivity(
        context: Context,
        killMe: Boolean,
        activity: Activity,
        targetClass: Activity
    )
    {
        val intent = Intent(context, targetClass::class.java)
        context.startActivity(intent)
        if (killMe) activity.finish()
    }



}