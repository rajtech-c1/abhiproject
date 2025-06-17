package abhimanpower.app.abhihire.contractorModule.apiFunctions

import abhimanpower.app.abhihire.contractorModule.modalClass.AddWorkDataResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.GetAvailableWorkersResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.WorkData
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddContractorResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddWorkerResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.ContractorData
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import okhttp3.MultipartBody


class ContractorApiFunction(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    contractorViewModel: ContractorViewModel,
    private val onAvailableWorkersFetched: (availableWorkersResponse: GetAvailableWorkersResponse) -> Unit = {},
    private val onAddWorkResponse: (addWorkDataResponse: AddWorkDataResponse) -> Unit = {},
    private val onUpdateContractorProfile: (addContractor: AddContractorResponse) -> Unit = {},
    ) {

    private var mContext: Context
    private var mLifecycleOwner: LifecycleOwner
    private var mcontractorViewModel: ContractorViewModel

    init {
        mContext = context
        mLifecycleOwner = lifecycleOwner
        mcontractorViewModel = contractorViewModel
    }

    fun getAvailableWorkers(workCategory: Int) {
        mcontractorViewModel.resetGetAvailableWorkersResponse()
        mcontractorViewModel.getAvailableWorkers(workCategory)
        mcontractorViewModel.getAvailableWorkersResponse().observe(mLifecycleOwner) {
            onAvailableWorkersFetched.invoke(it)
        }
    }


    fun addWork(workData: WorkData) {
        mcontractorViewModel.resetAddWorkResponse()
        mcontractorViewModel.addWork(workData)
        mcontractorViewModel.getAddWorkResponse().observe(mLifecycleOwner) {
            onAddWorkResponse.invoke(it)
        }
    }

    fun updateContractorProfile(contractorData: ContractorData, photo: MultipartBody.Part) {
        mcontractorViewModel.resetUpdateContractorResponse()
        mcontractorViewModel.updateContractorProfile(contractorData, photo)
        mcontractorViewModel.getUpdateContractorResponse().observe(mLifecycleOwner) {
            onUpdateContractorProfile.invoke(it)
//        }
        }
    }

}