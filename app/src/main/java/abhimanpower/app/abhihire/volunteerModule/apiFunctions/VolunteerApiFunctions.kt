package abhimanpower.app.abhihire.volunteerModule.apiFunctions

import abhimanpower.app.abhihire.volunteerModule.modalClass.AddWorkerResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetMonthlyStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetOverallStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkerData
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import okhttp3.MultipartBody


class VolunteerApiFunctions(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    volunteerViewModule: VolunteerViewModule,
    private val onAddWorkerResponse: (addWorker: AddWorkerResponse) -> Unit,
    private val onGetOverAllStatsResponse: (getOverallStatsResponse: GetOverallStatsResponse) -> Unit,
    private val onGetMonthlyStatsResponse: (getMonthlyStatsResponse: GetMonthlyStatsResponse) -> Unit = {},
    ) {

    private var mContext: Context
    private var mLifecycleOwner: LifecycleOwner
    private var mvolunteerViewModule: VolunteerViewModule

    init {
        mContext = context
        mLifecycleOwner = lifecycleOwner
        mvolunteerViewModule = volunteerViewModule
    }

    fun getOverallStats(volunteerId: Int) {
        mvolunteerViewModule.resetGetOverallStatsResponse()
        mvolunteerViewModule.getOverallStats(volunteerId)

        getOverallStatsObserver()
    }

    private fun getOverallStatsObserver() {
        mvolunteerViewModule.getOverallStatsResponse().observe(mLifecycleOwner) {
            if (it.status == 200) {
                onGetOverAllStatsResponse.invoke(it)
            } else {
                UtilFunctions.showToast(mContext, "Server Error")
            }
        }
    }

    fun getMonthlyStats(month: String,volunteerId: Int) {
        mvolunteerViewModule.resetGetMonthlyStatsResponse()
        mvolunteerViewModule.getMonthlyStats(month,volunteerId)

        getMonthlyStatsObserver()
    }

    private fun getMonthlyStatsObserver() {
        mvolunteerViewModule.getMonthlyStatsResponse().observe(mLifecycleOwner) {
            if (it.status == 200) {
                onGetMonthlyStatsResponse.invoke(it)
            } else {
                UtilFunctions.showToast(mContext, "Server Error")
            }
        }
    }

    fun postAddWorker(workerData: WorkerData, photo: MultipartBody.Part) {
        mvolunteerViewModule.resetAddWorkerResponse()
        mvolunteerViewModule.addWorker(workerData, photo)

        addWorkerObserver()
    }

    private fun addWorkerObserver() {
        mvolunteerViewModule.getAddWorkerResponse().observe(mLifecycleOwner) {

            onAddWorkerResponse.invoke(it)

//            if (it.status == 200) {
//                onAddWorkerResponse.invoke(it)
//            } else {
//                UtilFunctions.showToast(mContext, "Server Error")
//            }
        }
    }

}
