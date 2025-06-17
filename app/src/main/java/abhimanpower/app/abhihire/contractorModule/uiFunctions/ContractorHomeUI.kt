package abhimanpower.app.abhihire.contractorModule.uiFunctions

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.databinding.ActivityContractorHomeBinding
import abhimanpower.app.abhihire.databinding.BtmContractorSubscriptionBinding
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.paymentModule.PaymentPageActivity
import abhimanpower.app.abhihire.volunteerModule.adapters.WorkCategoryAdapter
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkCategory
import abhimanpower.app.abhihire.workerModule.adapter.DistrictAdapter
import abhimanpower.app.abhihire.workerModule.adapter.StateAdapter
import abhimanpower.app.abhihire.workerModule.modalClass.District
import abhimanpower.app.abhihire.workerModule.modalClass.State
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.AppData.workCategories
import abhimanpower.app.abhihire.zCommonFunctions.AreaData
import abhimanpower.app.abhihire.zCommonFunctions.CallIntent
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog


class ContractorHomeUI(
    context: Context,
    binding: ActivityContractorHomeBinding,
    private val activity: Activity,
    private val layoutInflater: LayoutInflater,
    private val onWorkCategorySelected: (workCategory: Int) -> Unit,
    private val onStateSelected: (stateId: Int) -> Unit = {},
    private val onDistrictSelected: (statedId: Int, districtId: Int) -> Unit = { _, _ -> }
) {
    private val mBinding: ActivityContractorHomeBinding
    private val mContext: Context

    init {
        mContext = context
        mBinding = binding

        setUserData()
        setVerificationStatus()
        setAnimation()
        initStateSpinner()
        initDistrictSpinner()
        initWorkCategorySpinner()

        setSpinnersTouchable()
    }

    private fun setUserData() {
        if (AppData.getUserRole(mContext) == 3) {
            //Contractor
            val contractorData = LoginCredentials.contractorAccountData

            mBinding.tvUserName.text = contractorData.name

            Log.e("Test","Contractor Image : ${contractorData.image}")

            if (contractorData.image.isNotEmpty()) {
                setProfileImage(contractorData.image)
            }
        }else{
            //General User
            Log.e("Test","General User Data")
        }
    }

    private fun setProfileImage(url: String) {
        Log.e("Test","Image Loading : $url")

        mBinding.ivProfile.load(url) {
            placeholder(R.drawable.ic_add_photo) // Make sure you have this drawable
            crossfade(true)
            crossfade(1000)
        }
    }


    private fun setSpinnersTouchable() {

        mBinding.spSelectWorkCategory.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                Log.e("Test", "A_UP")
                !checkWorkerSubscriptionStatus()

            } else {
                false
            }
        }

        mBinding.spSelectState.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                Log.e("Test", "A_UP")
                !checkWorkerSubscriptionStatus()

            } else {
                false
            }
        }

        mBinding.spSelectDistrict.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                Log.e("Test", "A_UP")
                !checkWorkerSubscriptionStatus()

            } else {
                false
            }
        }
    }


    private fun setVerificationStatus() {
        val workerData = LoginCredentials.contractorAccountData

        when (workerData.verificationStatus.toInt()) {
            1 -> {
                mBinding.tvSubscriptionStatus.text = "Subscribed"
                mBinding.ivVerification.setImageResource(R.drawable.verified)

            }

            2 -> {


                mBinding.tvSubscriptionStatus.text = "Not Subscribed"
                mBinding.ivVerification.setImageResource(R.drawable.ic_notsubscribed)
            }

            3 -> {

                mBinding.tvSubscriptionStatus.text = "Expired"
                mBinding.ivVerification.setImageResource(R.drawable.ic_notsubscribed)

            }
        }
    }

    fun checkWorkerSubscriptionStatus(): Boolean {
        val workerData = LoginCredentials.contractorAccountData

        when (workerData.verificationStatus.toInt()) {
            1 -> {
                //TODO User Subscribed - enable all
                return true
            }

            2 -> {
                //User Not Subscribed - ask to subscribe
                showSubscribeBtmSheet()
            }

            3 -> {
                //User Subscription expired - ask to renew
                showSubscribeBtmSheet()
            }
        }

        return false
    }


    fun showList() {
        mBinding.rvAvailableWorkers.visibility = View.VISIBLE
        mBinding.emptyList.visibility = View.GONE
    }

    fun hideList() {
        mBinding.rvAvailableWorkers.visibility = View.GONE
        mBinding.emptyList.visibility = View.VISIBLE
    }

    fun showPB(toShown: Boolean) {
        if (toShown) {
            mBinding.progresslayout.visibility = View.VISIBLE
        } else {
            mBinding.progresslayout.visibility = View.GONE
            mBinding.rvAvailableWorkers.visibility = View.VISIBLE
        }
    }

    private var workCategorySelected = -1

    private fun initWorkCategorySpinner(
    ) {
        val newItem = WorkCategory(
            0,
            "Select Work Category"
        )

        val allWorkCategoryList = mutableListOf(newItem) + AppData.workCategories


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

                    if (workCategorySelected != -1)
                        onWorkCategorySelected.invoke(workCategorySelected)

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle case when nothing is selected (optional)
                }
            }

    }

    fun setAnimation() {
//        val rotatingCircle = findViewById<ImageView>(R.id.rotatingCircle)
//        val centerIcon = findViewById<ImageView>(R.id.centerIcon)

// Start rotating animation
//        val rotate = AnimationUtils.loadAnimation(mContext, R.anim.rotate_infinite)
//        mBinding.progressCircle.startAnimation(rotate)

// List of icons to show in center
        val icons = listOf(
            R.drawable.loader_1,
            R.drawable.loader_2,
            R.drawable.loader_3,
            R.drawable.loader_4,
            R.drawable.loader_5,
            R.drawable.loader_6
        )

        var currentIcon = 0
        val handler = Handler(Looper.getMainLooper())

        val iconChanger = object : Runnable {
            override fun run() {
                mBinding.centerIcon.setImageResource(icons[currentIcon])
                val fade = AnimationUtils.loadAnimation(mContext, R.anim.fade_in_out)
                mBinding.centerIcon.startAnimation(fade)
                currentIcon = (currentIcon + 1) % icons.size
                handler.postDelayed(this, 1500)
            }
        }
        handler.post(iconChanger)

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
                        onStateSelected.invoke(stateSelected)
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

                    if (districtSelected != -1) {
                        onDistrictSelected.invoke(stateSelected, districtSelected)
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle case when nothing is selected (optional)
                }
            }
    }

    fun resetDistrictAndState() {
        mBinding.spSelectDistrict.setSelection(0)
        mBinding.spSelectState.setSelection(0)
    }

    private fun showSubscribeBtmSheet() {
        val dialog = BottomSheetDialog(mContext).apply {
            // Apply blur effect to the BOTTOMSHEET'S window (not activity)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                window?.setBackgroundBlurRadius(100) // 20 is enough (0-100)
            }
        }

        val binding: BtmContractorSubscriptionBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.btm_contractor_subscription,
            null,
            false
        )

        val planDetails = LoginCredentials.planDetails

        binding.planName.text = planDetails.planName
        binding.subscribeValue.text = planDetails.price

        binding.btSubscribe.setOnClickListener {
            CallIntent.gotoNextActivity(mContext, false, activity = activity, PaymentPageActivity())
        }

        dialog.setContentView(binding.root)
        dialog.show()
    }

}