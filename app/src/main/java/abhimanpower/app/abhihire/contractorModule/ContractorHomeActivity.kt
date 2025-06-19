package abhimanpower.app.abhihire.contractorModule

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.contractorModule.adapter.WorkersAdapter
import abhimanpower.app.abhihire.contractorModule.apiFunctions.ContractorApiFunction
import abhimanpower.app.abhihire.contractorModule.apiFunctions.ContractorViewModel
import abhimanpower.app.abhihire.contractorModule.modalClass.GetAvailableWorkersResponse
import abhimanpower.app.abhihire.contractorModule.uiFunctions.ContractorHomeUI
import abhimanpower.app.abhihire.databinding.ActivityContractorHomeBinding
import abhimanpower.app.abhihire.databinding.BtmSheetWorkerDetailsBinding
import abhimanpower.app.abhihire.loginModule.LogoutDialog
import abhimanpower.app.abhihire.workerModule.modalClass.WorkerAccountData
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.AreaData
import abhimanpower.app.abhihire.zCommonFunctions.CallIntent
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContractorHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContractorHomeBinding

    private lateinit var contractorViewModel: ContractorViewModel

    private lateinit var contractorHomeUI: ContractorHomeUI
    private lateinit var contractorApiFunction: ContractorApiFunction

    private lateinit var workersAdapter: WorkersAdapter

    private lateinit var logoutDialog: LogoutDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContractorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contractorViewModel = ViewModelProvider(this)[ContractorViewModel::class.java]

        initViews()
        setOnClickListeners()
    }

    private fun initViews() {
        logoutDialog = LogoutDialog(layoutInflater, this, ::onLogoutClicked)

        contractorHomeUI = ContractorHomeUI(
            this,
            binding,
            this,
            layoutInflater,
            ::onWorkCategorySelected,
            ::onStateSelected,
            ::onDistrictSelected
        )

        contractorApiFunction =
            ContractorApiFunction(this, this, contractorViewModel, ::onAvailableWorkersFetched)

        onWorkCategorySelected(1)
    }

    private fun onLogoutClicked() {
        AppData.putUserLoginStatus(this, false)
        CallIntent.gotoLoginActivity(this, true, this)
    }


    private fun setOnClickListeners() {

        binding.btClearFilter.setOnClickListener {
            contractorHomeUI.resetDistrictAndState()
            contractorHomeUI.showList()
            initAvailableWorkers(orgWorkersList)
        }

//        binding.btAddWork.setOnClickListener {
//            CallIntent.gotoMyWorksActivity(this, false, this)
//        }

        binding.btLogout.setOnClickListener {
            logoutDialog.openLogoutDialog()
        }

        binding.ivProfile.setOnClickListener {
            CallIntent.gotoNextActivity(this, false, this, ContractorProfileActivity())
        }

        binding.btPostedWorks.setOnClickListener {
            CallIntent.gotoMyWorksActivity(this, false, this)
        }

        binding.btPostNewWork.setOnClickListener {
            CallIntent.gotoAddWorkActivity(this, false, this)
        }
    }

    private fun onWorkCategorySelected(workCategory: Int) {
        contractorHomeUI.showPB(true)
        contractorApiFunction.getAvailableWorkers(workCategory)
    }

    private fun onAvailableWorkersFetched(response: GetAvailableWorkersResponse) {
        when (response.status) {
            200 -> {
                contractorHomeUI.showPB(false)
                if (response.workersList.isEmpty()) {
                    contractorHomeUI.hideList()
                } else {
                    contractorHomeUI.showList()
                    orgWorkersList = response.workersList
                    initAvailableWorkers(response.workersList)
                }
                Log.e("Test", "Array : ${response.workersList}")
            }

            201 -> {
                contractorHomeUI.showPB(false)
                contractorHomeUI.hideList()

            }

            else -> {
                UtilFunctions.showToast(this, "Server Error")
            }
        }
    }

    private lateinit var orgWorkersList: ArrayList<WorkerAccountData>

    private fun initAvailableWorkers(worksList: ArrayList<WorkerAccountData>) {

        if (worksList.isEmpty())
            contractorHomeUI.hideList()

        workersAdapter = WorkersAdapter(this, worksList, ::onWorkerClicked)

        binding.rvAvailableWorkers.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )
            adapter = workersAdapter
        }

        binding.rvAvailableWorkers.isNestedScrollingEnabled = false
    }

    private fun onWorkerClicked(workerAccountData: WorkerAccountData) {
        Log.e("Test", "Worker Clicked")

        if (contractorHomeUI.checkWorkerSubscriptionStatus())
            showCustomBottomSheetWithDataBinding(workerAccountData)
    }

    private fun showCustomBottomSheetWithDataBinding(workerAccountData: WorkerAccountData) {
        val dialog = BottomSheetDialog(this).apply {
            // Apply blur effect to the BOTTOMSHEET'S window (not activity)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                window?.setBackgroundBlurRadius(100) // 20 is enough (0-100)
            }
        }

        val binding: BtmSheetWorkerDetailsBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.btm_sheet_worker_details,
            null,
            false
        )

        if (workerAccountData.image.isNotEmpty())
            binding.profilePic.load(workerAccountData.image) {
                placeholder(R.drawable.ic_add_photo) // Make sure you have this drawable
                crossfade(true)
                crossfade(1000)
            }
        else
            binding.profilePic.setImageResource(R.drawable.ic_shop_owner)

        binding.workerName.text = workerAccountData.name
        binding.tvWorkCategory.text =
            AppData.workCategories[workerAccountData.workCategory.toInt()].categoryName

        binding.tvDistrictValue.text = AreaData.getDistrict(
            workerAccountData.district.toInt(),
            workerAccountData.state.toInt()
        )
        binding.tvState.text = AreaData.getState(workerAccountData.state.toInt() - 1)
        binding.tvExperienceValue.text = "${workerAccountData.experience} years"

        binding.btCallNow.setOnClickListener {
            UtilFunctions.openDialer(this, workerAccountData.mobileNo)
        }

        dialog.setContentView(binding.root)
        dialog.show()
    }

    //Search Code

    private fun onStateSelected(stateId: Int) {
        val filterByState = orgWorkersList.filter { workerAccountData ->
            workerAccountData.state.toInt() == stateId
        }

        initAvailableWorkers(filterByState as ArrayList<WorkerAccountData>)
    }

    private fun onDistrictSelected(statedId: Int, districtId: Int) {
        val filterByDistrictState = orgWorkersList.filter { workerAccountData ->
            workerAccountData.state.toInt() == statedId && workerAccountData.district.toInt() == districtId

        }

        initAvailableWorkers(filterByDistrictState as ArrayList<WorkerAccountData>)
    }
}