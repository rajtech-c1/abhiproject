package abhimanpower.app.abhihire.contractorModule.apiFunctions

import abhimanpower.app.abhihire.contractorModule.modalClass.AddWorkDataResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.GetAvailableWorkersResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.WorkData
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddWorkerResponse
import android.content.Context
import androidx.lifecycle.LifecycleOwner


class ContractorApiFunction(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    contractorViewModel: ContractorViewModel,
    private val onAvailableWorkersFetched: (availableWorkersResponse: GetAvailableWorkersResponse) -> Unit = {},
    private val onAddWorkResponse: (addWorkDataResponse: AddWorkDataResponse) -> Unit = {},


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


    fun addWork(workData: WorkData)
    {
        mcontractorViewModel.resetAddWorkResponse()
        mcontractorViewModel.addWork(workData)
        mcontractorViewModel.getAddWorkResponse().observe(mLifecycleOwner){
            onAddWorkResponse.invoke(it)
        }
    }
}