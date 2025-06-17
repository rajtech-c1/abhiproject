package abhimanpower.app.abhihire.contractorModule.uiFunctions

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.databinding.ActivityEditContractorProfileBinding
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.volunteerModule.modalClass.ContractorData
import abhimanpower.app.abhihire.workerModule.adapter.DistrictAdapter
import abhimanpower.app.abhihire.workerModule.adapter.StateAdapter
import abhimanpower.app.abhihire.workerModule.modalClass.District
import abhimanpower.app.abhihire.workerModule.modalClass.State
import abhimanpower.app.abhihire.zCommonFunctions.AreaData
import abhimanpower.app.abhihire.zCommonFunctions.AreaData.andhraPradeshDistricts
import abhimanpower.app.abhihire.zCommonFunctions.AreaData.telanganaDistricts
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.AdapterView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class EditContractorProfileUI(
    context: Context,
    binding: ActivityEditContractorProfileBinding,
    private val onValuated: (contractorData: ContractorData) -> Unit = {},
) {
    private val mBinding: ActivityEditContractorProfileBinding
    private val mContext: Context

    init {
        mContext = context
        mBinding = binding

        genderOnClickListener()
        initStateSpinner()
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

    private fun validateRegisterForm() {
        val eName = mBinding.etUserName.text.toString()
        val eMobileNo = mBinding.etMobileNo.text.toString()
        val eEmailId = mBinding.etEmail.text.toString()
        val eStreet = mBinding.etStreet.text.toString()
        val eArea = mBinding.etArea.text.toString()
        val ePincode = mBinding.etPincode.text.toString()

        if (eName.isEmpty()) {
            UtilFunctions.showToast(mContext, "Enter Name")
            return
        }

        if (eMobileNo.isEmpty()) {
            UtilFunctions.showToast(mContext, "Enter MobileNo")
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



        if (stateSelected == -1) {
            UtilFunctions.showToast(mContext, "Select State")
            return
        }

        if (districtSelected == -1) {
            UtilFunctions.showToast(mContext, "Select District")
            return
        }


        val contractorData = ContractorData(
            eName,
            eMobileNo,
            eEmailId,
            eStreet,
            eArea,
            ePincode,
            district = districtSelected.toString(),
            stateSelected.toString(),
            2,
            0,
            2
        )


        onValuated.invoke(contractorData)
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
    private var selectedDate = LoginCredentials.workerAccountData.dob

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

        mBinding.spSelectState.setSelection(LoginCredentials.contractorAccountData.state.toInt())
    }

    private fun setDistrict() {
        val districtId = LoginCredentials.contractorAccountData.district.toInt()

        when (stateSelected) {
            1 -> {
                val position = telanganaDistricts.indexOfFirst { it.districtId == districtId }
                mBinding.spSelectDistrict.setSelection(position + 1)
            }

            2 -> {
                val position = andhraPradeshDistricts.indexOfFirst { it.districtId == districtId }
                mBinding.spSelectDistrict.setSelection(position + 1)

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

        setDistrict()
    }


}