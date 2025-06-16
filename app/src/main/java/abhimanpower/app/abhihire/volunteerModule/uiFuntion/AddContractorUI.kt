package abhimanpower.app.abhihire.volunteerModule.uiFuntion

import abhimanpower.app.abhihire.databinding.ActivityAddContractorBinding
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.volunteerModule.modalClass.ContractorData
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


class AddContractorUI(
    context: Context,
    binding: ActivityAddContractorBinding,
    private val onValuated: (contractorData: ContractorData) -> Unit,
) {
    private val mBinding: ActivityAddContractorBinding
    private val mContext: Context

    init {
        mContext = context
        mBinding = binding

        initStateSpinner()
        onClickListeners()
    }

    private fun onClickListeners() {
        mBinding.btSubmit.setOnClickListener {
            validateRegisterForm()
        }

    }

    fun validateRegisterForm() {
        val eName = mBinding.etUserName.text.toString()
        val eMobileNo = mBinding.etMobileNo.text.toString()
        val eEmail = mBinding.etEmail.text.toString()
        val eStreet = mBinding.etStreet.text.toString()
        val eArea = mBinding.etArea.text.toString()
        val ePincode = mBinding.etPincode.text.toString()
//        val eState = mBinding.etState.text.toString()



        if (eName.isEmpty()) {
            UtilFunctions.showToast(mContext, "Enter Name")
            return
        }

        if(eEmail.isEmpty())
        {
            UtilFunctions.showToast(mContext,"Enter Email")
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

        if(stateSelected==-1) {
            UtilFunctions.showToast(mContext, "Select State")
            return
        }

        if(districtSelected==-1) {
            UtilFunctions.showToast(mContext, "Select District")
            return
        }

        val contractorData = ContractorData(
            eName,
            eMobileNo,
            eEmail,
            eStreet,
            eArea,
            ePincode,
            district = districtSelected.toString(),
            stateSelected.toString(),
            2,
            LoginCredentials.volunteerAccountData.volunteerId.toInt(),
            2
        )


        onValuated.invoke(contractorData)
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