package abhimanpower.app.abhihire.volunteerModule.uiFuntion

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.databinding.ActivityAddWorkerBinding
import abhimanpower.app.abhihire.volunteerModule.adapters.WorkCategoryAdapter
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkCategory
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkerData
import abhimanpower.app.abhihire.workerModule.adapter.DistrictAdapter
import abhimanpower.app.abhihire.workerModule.adapter.StateAdapter
import abhimanpower.app.abhihire.workerModule.modalClass.District
import abhimanpower.app.abhihire.workerModule.modalClass.State
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.AppData.workCategories
import abhimanpower.app.abhihire.zCommonFunctions.AreaData
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.AdapterView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class AddWorkerUI(
    context: Context,
    binding: ActivityAddWorkerBinding,
    private val onValuated: (workerData: WorkerData) -> Unit,
) {
    private val mBinding: ActivityAddWorkerBinding
    private val mContext: Context

    init {
        mContext = context
        mBinding = binding

        genderOnClickListener()
        initStateSpinner()
        initWorkCategorySpinner()
        onClickListeners()
    }

    private fun onClickListeners() {
        mBinding.btSubmit.setOnClickListener {
            validateRegisterForm()
        }

        mBinding.etSelectDate.setOnClickListener {
            initDatePicker()
        }
    }

    fun validateRegisterForm() {
        val eName = mBinding.etUserName.text.toString()
        val eMobileNo = mBinding.etMobileNo.text.toString()
        val eStreet = mBinding.etStreet.text.toString()
        val eArea = mBinding.etArea.text.toString()
        val ePincode = mBinding.etPincode.text.toString()
        val eState = mBinding.etState.text.toString()

        val eExperience = mBinding.etExperience.text.toString()


        if (eName.isEmpty()) {
            UtilFunctions.showToast(mContext, "Enter Name")
            return
        }

        if (eMobileNo.length!=10) {
            UtilFunctions.showToast(mContext, "Enter Valid MobileNo")
            return
        }

        if (selectedDate.isEmpty()) {
            UtilFunctions.showToast(mContext, "Enter Date Of Birth")
            return
        }

        if (nGenderSelected == -1) {
            UtilFunctions.showToast(mContext, "Enter Gender")
            return
        }

        if (eStreet.isEmpty()) {
            UtilFunctions.showToast(mContext, "Enter Street")
            return
        }

        if (eArea.isEmpty()) {
            UtilFunctions.showToast(mContext, "Enter Area")
            return
        }

        if (ePincode.isEmpty()) {
            UtilFunctions.showToast(mContext, "Enter Pincode")
            return
        }

//        if (eState.isEmpty()) {
//            UtilFunctions.showToast(mContext, "Enter State")
//            return
//        }

        if(stateSelected==-1) {
            UtilFunctions.showToast(mContext, "Select State")
            return
        }

        if(districtSelected==-1) {
            UtilFunctions.showToast(mContext, "Select District")
            return
        }

        if (workCategorySelected == -1) {
            UtilFunctions.showToast(mContext, "Select Work Category")
            return
        }

        if (eExperience.isEmpty()) {
            UtilFunctions.showToast(mContext, "Enter Experience")
            return
        }

        val workerData = WorkerData(
            eName,
            eMobileNo,
            dob = selectedDate,
            nGenderSelected,
            eStreet,
            eArea,
            ePincode,
            stateSelected,
            districtSelected,
            workCategorySelected,
            eExperience,
            2
        )


        onValuated.invoke(workerData)
    }

    private var nGenderSelected = -1
    private fun genderOnClickListener() {
        mBinding.rgGender.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbFemale -> {
                    nGenderSelected = 1

                }

                R.id.rbMale -> {
                    nGenderSelected = 2
                }

                R.id.rbOther -> {
                    nGenderSelected = 0
                }
            }
        }
    }


    private val cal = Calendar.getInstance()
    private var selectedDate = ""

    private fun initDatePicker() {

        val calendar = Calendar.getInstance()
//        calendar.time = minimumDate

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePic = DatePickerDialog(
            mContext, dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePic.show()
    }


    private val dateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy" // mention the format you need
            val myFormat1 = "yyyy-MM-dd"
            val sdfCalc = SimpleDateFormat(myFormat, Locale.US)

            val dbFormat = SimpleDateFormat(myFormat1, Locale.US)

            val selectedDateForCalculation = sdfCalc.format(cal.time)
            val selectedDateDBFormat = dbFormat.format(cal.time)

            binding.etSelectDate.text = "${selectedDateForCalculation}"

            selectedDate = selectedDateDBFormat

        }

    private var workCategorySelected = -1

    private fun initWorkCategorySpinner(
    ) {

        val newItem = WorkCategory(
            0,
            "Select Work Category"
        )


        val allWorkCategoryList = listOf(newItem) + AppData.workCategories

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