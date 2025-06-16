package abhimanpower.app.abhihire.contractorModule.uiFunctions

import abhimanpower.app.abhihire.contractorModule.modalClass.WorkData
import abhimanpower.app.abhihire.databinding.ActivityAddWorkBinding
import abhimanpower.app.abhihire.databinding.ActivityAddWorkerBinding
import abhimanpower.app.abhihire.volunteerModule.adapters.WorkCategoryAdapter
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkCategory
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkerData
import abhimanpower.app.abhihire.workerModule.adapter.DistrictAdapter
import abhimanpower.app.abhihire.workerModule.adapter.StateAdapter
import abhimanpower.app.abhihire.workerModule.modalClass.District
import abhimanpower.app.abhihire.workerModule.modalClass.State
import abhimanpower.app.abhihire.zCommonFunctions.AreaData
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.content.Context
import android.view.View
import android.widget.AdapterView


class AddWorkUI(
    context: Context,
    binding: ActivityAddWorkBinding,
    private val onValuated: (workData : WorkData) -> Unit,
) {
    private val mBinding: ActivityAddWorkBinding
    private val mContext: Context

    init {
        mContext = context
        mBinding = binding

        initStateSpinner()
        initWorkCategorySpinner()
        onClickListeners()
    }

    private fun onClickListeners() {
        mBinding.btSubmit.setOnClickListener {
            validateRegisterForm()
        }


    }

    fun validateRegisterForm() {
        val eName = mBinding.etUserName.text.toString()

        val eDescription = mBinding.etDescription.text.toString()

        val eArea = mBinding.etArea.text.toString()
        val ePincode = mBinding.etPincode.text.toString()

        if (workCategorySelected == -1) {
            UtilFunctions.showToast(mContext, "Select Work Category")
            return
        }

        if (eName.isEmpty()) {
            UtilFunctions.showToast(mContext, "Enter Name")
            return
        }

        if (eDescription.isEmpty()) {
            UtilFunctions.showToast(mContext, "Enter Description")
            return
        }

        if (eArea.isEmpty()) {
            UtilFunctions.showToast(mContext, "Enter Area")
            return
        }

        if (ePincode.length!=6) {
            UtilFunctions.showToast(mContext, "Enter Valid Pincode")
            return
        }

        if(stateSelected==-1) {
            UtilFunctions.showToast(mContext, "Select State")
            return
        }

        if(districtSelected==-1) {
            UtilFunctions.showToast(mContext, "Select District")
            return
        }




        val workerData = WorkData(
            eName,
            eDescription,
            eArea,
            ePincode,
            stateSelected,
            districtSelected,
            workCategorySelected,
            2
        )


        onValuated.invoke(workerData)
    }


    private var workCategorySelected = -1

    private fun initWorkCategorySpinner(
    ) {

        val newItem = WorkCategory(
            0,
            "Select Month"
        )

        val workCategories: List<WorkCategory> = listOf(
            WorkCategory(1, "Design"),
            WorkCategory(2, "Development"),
            WorkCategory(3, "Testing"),
            WorkCategory(4, "Deployment"),
            WorkCategory(5, "Documentation"),
            WorkCategory(6, "Client Meeting"),
            WorkCategory(7, "Bug Fixing"),
            WorkCategory(8, "Code Review"),
            WorkCategory(9, "Research"),
            WorkCategory(10, "Maintenance")
        )


        val allWorkCategoryList = listOf(newItem) + workCategories

        val adapter = WorkCategoryAdapter(
            mContext,
            android.R.layout.simple_spinner_item,
            allWorkCategoryList
        )

        mBinding.spSelectWorkCategory.adapter = adapter
        mBinding.spSelectWorkCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    workCategorySelected = if (position != 0)
                        workCategories[position - 1].categoryId
                    else {
                        -1
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle case when nothing is selected (optional)
                }
            }
    }

    private var stateSelected = -1

    private fun initStateSpinner(
    ) {

        val newItem = State(
            0, "Select State"
        )

        val allStates = listOf(newItem) + AreaData.states

        val adapter = StateAdapter(
            mContext,
            android.R.layout.simple_spinner_item,
            allStates
        )

        mBinding.spSelectState.adapter = adapter
        mBinding.spSelectState.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    stateSelected = if (position != 0)
                        AreaData.states[position - 1].stateId
                    else {
                        -1
                    }

                    if (stateSelected != -1) {
                        initDistrictSpinner()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle case when nothing is selected (optional)
                }
            }
    }

    private var districtSelected = -1

    private fun initDistrictSpinner(
    ) {

        val newItem = District(
            0, "Select District"
        )

        val allDistricts = mutableListOf(newItem)

        when (stateSelected) {
            1 -> {
                allDistricts += AreaData.telanganaDistricts
            }

            2 -> {
                allDistricts += AreaData.andhraPradeshDistricts
            }
        }


        val adapter = DistrictAdapter(
            mContext,
            android.R.layout.simple_spinner_item,
            allDistricts
        )

        mBinding.spSelectDistrict.adapter = adapter
        mBinding.spSelectDistrict.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    districtSelected = if (position != 0)
                        allDistricts[position].districtId
                    else {
                        -1
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle case when nothing is selected (optional)
                }
            }
    }

}