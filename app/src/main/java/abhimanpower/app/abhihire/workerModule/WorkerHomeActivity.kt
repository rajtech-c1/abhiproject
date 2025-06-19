package abhimanpower.app.abhihire.workerModule

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.databinding.ActivityWorkerHomeBinding
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkCategory
import abhimanpower.app.abhihire.workerModule.adapter.AvailableWorksAdapter
import abhimanpower.app.abhihire.workerModule.apiFunctions.WorkerApiFunction
import abhimanpower.app.abhihire.workerModule.apiFunctions.WorkerViewModel
import abhimanpower.app.abhihire.workerModule.modalClass.District
import abhimanpower.app.abhihire.workerModule.modalClass.GetAvailableWorksResponse
import abhimanpower.app.abhihire.workerModule.modalClass.SelectedData
import abhimanpower.app.abhihire.workerModule.modalClass.State
import abhimanpower.app.abhihire.workerModule.modalClass.WorkData
import abhimanpower.app.abhihire.workerModule.uiFunctions.WorkerHomeUI
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.AreaData
import abhimanpower.app.abhihire.zCommonFunctions.CallIntent
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkerHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkerHomeBinding
    private lateinit var workerViewModel: WorkerViewModel

    private lateinit var workerApiFunction: WorkerApiFunction

    private lateinit var availableWorksAdapter: AvailableWorksAdapter

    private lateinit var workerHomeUI: WorkerHomeUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        workerViewModel = ViewModelProvider(this)[WorkerViewModel::class.java]

        initView()
        setOnClickListeners()
        setSearchBarListener()
    }

    private fun setOnClickListeners() {
        binding.ivProfile.setOnClickListener {
            CallIntent.gotoWorkerProfileActivity(this, false, this)
        }


        binding.etSearch
            .setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus && !workerHomeUI.checkWorkerSubscriptionStatus()) {
                    binding.etSearch.clearFocus()
                }
            }


        binding.btFilter.setOnClickListener {
            if (workerHomeUI.checkWorkerSubscriptionStatus()) {
                callFilterDialogCode()
            }
        }

        binding.btClearFilter.setOnClickListener {
            workerHomeUI.showList()
            binding.etSearch.text.clear()
            initAvailableWorks(orgWorkList)
        }


    }

    private fun initView() {

        binding.btClearFilter.paintFlags =
            binding.btClearFilter.paintFlags or Paint.UNDERLINE_TEXT_FLAG


        workerApiFunction =
            WorkerApiFunction(
                this,
                this,
                workerViewModel,
                ::onGetAvailableWorksResponseFetched
            )

        workerHomeUI = WorkerHomeUI(this, this, binding, layoutInflater)


        getAvailableWorks()
    }


    private fun getAvailableWorks() {
        workerHomeUI.showPB(true)
        workerApiFunction.getAvailableWorks()
    }

    private fun onGetAvailableWorksResponseFetched(response: GetAvailableWorksResponse) {
        when (response.status) {
            200 -> {
                workerHomeUI.showPB(false)
                if (response.worksList.isNotEmpty()) {
                    orgWorkList = response.worksList
                    initAvailableWorks(response.worksList)
                } else
                    UtilFunctions.showToast(this, "No Works Are Available")
            }

            else -> {
                UtilFunctions.showToast(this, "Server Error")
            }
        }
    }

    private lateinit var orgWorkList: ArrayList<WorkData>

    private fun initAvailableWorks(worksList: ArrayList<WorkData>) {

        if (worksList.isEmpty())
            workerHomeUI.hideList()
        else
            workerHomeUI.showList()

        availableWorksAdapter = AvailableWorksAdapter(this, worksList, ::onWorkClicked)

        binding.rvAvailableWorks.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )
            adapter = availableWorksAdapter
        }

        binding.rvAvailableWorks.isNestedScrollingEnabled = false
    }

    private fun onWorkClicked(workData: WorkData) {

        if (workerHomeUI.checkWorkerSubscriptionStatus()) {
            SelectedData.workData = workData
            CallIntent.gotoWorkDetailsActivity(this, false, this)
        }
    }

    fun setSearchBarListener() {
        binding.etSearch.addTextChangedListener { text ->
            val query = text.toString().trim().lowercase()

            val filtered = orgWorkList.filter { item ->
                item.workName.lowercase().contains(query) || item.pincode.contains(query)
            }

            initAvailableWorks(filtered as ArrayList<WorkData>)

            if (text.isNullOrEmpty()) {
                binding.etSearch.clearFocus()
                workerHomeUI.showList()
            }

        }

    }

    private fun callFilterDialogCode() {
        showFilterDialog(
            this,
            AppData.workCategories,
            AreaData.states
        ) { selectedCategoryId, selectedAreaId, selectedDistrictId ->

            val filteredList = orgWorkList.filter { work ->

                val matchesCategory = selectedCategoryId == null ||
                        work.workCategory == selectedCategoryId.categoryId.toString()

                val matchesArea = selectedAreaId == null ||
                        work.state.toInt() == selectedAreaId.stateId

                val matchesDistrict = selectedDistrictId == null ||
                        work.district.toInt() == selectedDistrictId.districtId

                matchesCategory && matchesArea && matchesDistrict
            }

            initAvailableWorks(filteredList as ArrayList<WorkData>)

        }

    }

    private fun showFilterDialog(
        context: Context,
        categoryList: List<WorkCategory>,
        stateList: List<State>,
        onFilterApplied: (selectedCategory: WorkCategory?, selectedArea: State?, selectedDistrict: District?) -> Unit
    ) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_filter, null)

        val spinnerCategory = dialogView.findViewById<Spinner>(R.id.spinnerCategory)
        val spinnerState = dialogView.findViewById<Spinner>(R.id.spinnerState)
        val spinnerDistrict = dialogView.findViewById<Spinner>(R.id.spinnerDistrcit)


        // Add default "All" item
        val categoryNames = listOf("All Categories") + categoryList.map { it.categoryName }
        val stateName = listOf("All Areas") + stateList.map { it.stateName }
        var districtsList: List<String> = ArrayList<String>()

        spinnerCategory.adapter =
            ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, categoryNames)
        spinnerState.adapter =
            ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, stateName)

        spinnerState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    1 -> {
                        districtsList =
                            listOf("All Districts") + AreaData.telanganaDistricts.map { it.districtName }
                    }

                    2 -> {
                        districtsList =
                            listOf("All Districts") + AreaData.andhraPradeshDistricts.map { it.districtName }
                    }
                    //TODO add other 3 states
                }

                spinnerDistrict.adapter =
                    ArrayAdapter(
                        context,
                        android.R.layout.simple_spinner_dropdown_item,
                        districtsList
                    )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case when nothing is selected (optional)
            }
        }




        AlertDialog.Builder(context)
            .setTitle("Apply Filters")
            .setView(dialogView)
            .setPositiveButton("Apply") { dialog, _ ->
                val selectedCategoryPosition = spinnerCategory.selectedItemPosition
                val selectedAreaPosition = spinnerState.selectedItemPosition

                val selectedDistrictPosition = spinnerDistrict.selectedItemPosition

                val selectedCategory =
                    if (selectedCategoryPosition == 0) null else categoryList[selectedCategoryPosition - 1]
                val selectedArea =
                    if (selectedAreaPosition == 0) null else stateList[selectedAreaPosition - 1]

                var selectedDistrict: District? = null

                if (selectedArea != null)
                    when (selectedArea.stateId) {
                        1 -> {
                            selectedDistrict =
                                if (selectedDistrictPosition == 0) null else AreaData.telanganaDistricts[selectedDistrictPosition - 1]

                        }

                        2 -> {
                            selectedDistrict =
                                if (selectedDistrictPosition == 0) null else AreaData.andhraPradeshDistricts[selectedDistrictPosition - 1]

                        }
                        //TODO add 3 more states here

                    }


                onFilterApplied(selectedCategory, selectedArea, selectedDistrict)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

}