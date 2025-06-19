package abhimanpower.app.abhihire.workerModule.uiFunctions

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.databinding.ActivityWorkerHomeBinding
import abhimanpower.app.abhihire.databinding.BtmSubscriptionSheetBinding
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.paymentModule.PaymentPageActivity
import abhimanpower.app.abhihire.volunteerModule.modalClass.MonthData
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.CallIntent
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog


class WorkerHomeUI(
    context: Context,
    private val activity: Activity,
    binding: ActivityWorkerHomeBinding,
    layoutInflater: LayoutInflater,
    private val onMonthSelected: (monthData: MonthData) -> Unit = {},
) {
    private val mBinding: ActivityWorkerHomeBinding
    private val mContext: Context

    private val mLayoutInflater: LayoutInflater

    init {
        mContext = context
        mBinding = binding
        mLayoutInflater = layoutInflater

        setVerificationStatus()
        setAnimation()

        setUserData()
    }

    private fun setUserData() {
            //Contractor
            val workerData = LoginCredentials.workerAccountData

            mBinding.tvUserName.text = workerData.name

            Log.e("Test","Worker Image : ${workerData.image}")

            if (workerData.image.isNotEmpty()) {
                mBinding.ivProfile.load(workerData.image) {
                    placeholder(R.drawable.ic_add_photo) // Make sure you have this drawable
                    crossfade(true)
                    crossfade(1000)
                }
            }

    }


    private fun setVerificationStatus()
    {
        val workerData = LoginCredentials.workerAccountData

        when (workerData.verificationStatus.toInt()) {
            1 -> {
                mBinding.tvSubscriptionStatus.text="Subscribed"
                mBinding.ivVerification.setImageResource(R.drawable.verified)

            }

            2 -> {
                mBinding.tvSubscriptionStatus.text="Not Subscribed"
                mBinding.ivVerification.setImageResource(R.drawable.ic_notsubscribed)
            }

            3 -> {
                mBinding.tvSubscriptionStatus.text="Expired"
                mBinding.ivVerification.setImageResource(R.drawable.ic_notsubscribed)

            }
        }
    }

    fun checkWorkerSubscriptionStatus(): Boolean {
        val workerData = LoginCredentials.workerAccountData

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
        mBinding.rvAvailableWorks.visibility = View.VISIBLE
        mBinding.emptyList.visibility = View.GONE
    }

    fun hideList() {
        mBinding.rvAvailableWorks.visibility = View.GONE
        mBinding.emptyList.visibility = View.VISIBLE
    }

    fun showPB(toShown: Boolean) {
        if (toShown) {
            mBinding.progresslayout.visibility = View.VISIBLE
        } else {
            mBinding.progresslayout.visibility = View.GONE
            mBinding.rvAvailableWorks.visibility = View.VISIBLE
        }
    }

    private fun showSubscribeBtmSheet() {
        Log.e("Test","Worker Subscription Sheet")
        val dialog = BottomSheetDialog(mContext)

        val binding: BtmSubscriptionSheetBinding = DataBindingUtil.inflate(
            mLayoutInflater,
            R.layout.btm_subscription_sheet,
            null,
            false
        )

        val planDetails =LoginCredentials.planDetails

        binding.planName.text = planDetails.planName
        binding.subscribeValue.text=planDetails.price

        binding.btSubscribe.setOnClickListener {
            CallIntent.gotoNextActivity(mContext, false, activity = activity, PaymentPageActivity())
        }

        dialog.setContentView(binding.root)
        dialog.show()
    }

    private fun setAnimation() {
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


}