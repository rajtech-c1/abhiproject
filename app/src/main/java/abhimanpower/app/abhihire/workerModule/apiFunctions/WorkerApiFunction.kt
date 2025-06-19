package abhimanpower.app.abhihire.workerModule.apiFunctions

import abhimanpower.app.abhihire.volunteerModule.modalClass.AddWorkerResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkerData
import abhimanpower.app.abhihire.workerModule.modalClass.GetAvailableWorksResponse
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import okhttp3.MultipartBody


class WorkerApiFunction(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    workerViewModel: WorkerViewModel,
    private val onGetAvailableWorksResponse: (getAvailableWorksResponse: GetAvailableWorksResponse) -> Unit = {},
    private val onUpdateWorkerProfile: (addWorker: AddWorkerResponse) -> Unit = {},
    private val onPostDeleted: (response: GetAvailableWorksResponse) -> Unit = {},
) {

    private var mContext: Context
    private var mLifecycleOwner: LifecycleOwner
    private var mvolunteerViewModule: WorkerViewModel

    init {
        mContext = context
        mLifecycleOwner = lifecycleOwner
        mvolunteerViewModule = workerViewModel
    }

    fun getAvailableWorks() {
        mvolunteerViewModule.resetGetAvailableWorksResponse()
        mvolunteerViewModule.getAvailableWorks()

        getAvailableWorksObserver()
    }

    private fun getAvailableWorksObserver() {
        mvolunteerViewModule.getAvailableWorksResponse().observe(mLifecycleOwner) {
            onGetAvailableWorksResponse.invoke(it)
        }
    }

    fun updateWorkerProfile(workerData: WorkerData, photo: MultipartBody.Part) {
        mvolunteerViewModule.resetUpdateWorkerResponse()
        mvolunteerViewModule.updateWorkerProfile(workerData, photo)

        updateWorkerObserver()
    }

    private fun updateWorkerObserver() {
        mvolunteerViewModule.getUpdateWorkerResponse().observe(mLifecycleOwner) {

            onUpdateWorkerProfile.invoke(it)

//            if (it.status == 200) {
//                onAddWorkerResponse.invoke(it)
//            } else {
//                UtilFunctions.showToast(mContext, "Server Error")
//            }
        }
    }

    fun deletePost(postId: Int) {
        mvolunteerViewModule.resetDeletePostResponse()
        mvolunteerViewModule.deletePost(postId)

        mvolunteerViewModule.getDeletePostResponse().observe(mLifecycleOwner)
        {
            onPostDeleted.invoke(it)
        }
    }

}