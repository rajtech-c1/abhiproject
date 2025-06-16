package abhimanpower.app.abhihire.loginModule.uiFunctions

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.databinding.ActivityVolunteerLoginBinding
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.content.Context
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView


class LoginUI(
    context: Context,
    binding: ActivityVolunteerLoginBinding,
    private val onOTPValidate: (isOTPCorrect: Boolean) -> Unit = {},
) {
    private val mBinding: ActivityVolunteerLoginBinding
    private val mContext: Context

    init {
        mContext = context
        mBinding = binding

        setAnimation()

        when (LoginCredentials.selectedRole) {
            1 -> {
                setWorkerUI()
            }

            2 -> {
                setVolunteerUI()
            }

            3 -> {
                setContractorUI()
            }
        }
    }

    fun showNumberScreen() {
        mBinding.loginLayout.visibility = View.VISIBLE
        mBinding.otpLayout.visibility = View.GONE
    }

    private var otpValue = "1234"

    fun setOTPValue(otpSent:String)
    {
        otpValue=otpSent
    }

    fun showOTPScreen() {

        mBinding.loginLayout.visibility = View.GONE
        mBinding.otpLayout.visibility = View.VISIBLE

        showTimer(true)
    }

    fun showPB(toShow: Boolean) {
        if (toShow) {
            mBinding.ltLoginBtn.visibility = View.GONE
            mBinding.progresslayout.visibility = View.VISIBLE
        } else {
            mBinding.ltLoginBtn.visibility = View.VISIBLE
            mBinding.progresslayout.visibility = View.GONE
        }
    }

    fun showError(toShow: Boolean, message: String) {
        if (toShow) {
            mBinding.tvErrorMessage.visibility = View.VISIBLE
            mBinding.tvErrorMessage.text = message
        } else {
            mBinding.tvErrorMessage.visibility = View.GONE
        }
    }

    private fun setVolunteerUI() {
        mBinding.tvLoginTitle.text = "Volunteer Login"
        mBinding.tvSubTitle.text = "login to conitune to your account"

        mBinding.tvNoAccount.visibility = View.GONE
        mBinding.btRegister.visibility = View.GONE
    }

    private fun setWorkerUI() {
        mBinding.tvLoginTitle.text = "Worker Login"

        mBinding.tvNoAccount.visibility = View.VISIBLE
        mBinding.btRegister.visibility = View.VISIBLE
    }

    private fun setContractorUI() {
        mBinding.tvLoginTitle.text = "Contractor Login"

        mBinding.tvNoAccount.visibility = View.VISIBLE
        mBinding.btRegister.visibility = View.VISIBLE
    }

    fun validateOTP() {
        val sentOTP=otpValue
        val digit1 = mBinding.etOtp1.text.toString()
        val digit2 = mBinding.etOtp2.text.toString()
        val digit3 = mBinding.etOtp3.text.toString()
        val digit4 = mBinding.etOtp4.text.toString()

        val eotpValue = digit1 + digit2 + digit3 + digit4

        if (sentOTP != eotpValue) {
            UtilFunctions.showToast(mContext, "Enter Valid OTP")
            return
        }

        onOTPValidate.invoke(true)
    }

    fun showTimer(isShow: Boolean) {
        if (isShow) {

            startOtpCountdownTimer(mBinding.tvRetryTimer, 90, onTimerFinish = {
                showTimer(false)
            })

            mBinding.tvRetry.visibility = View.VISIBLE
            mBinding.tvRetryTimer.visibility = View.VISIBLE
            mBinding.btReSend.visibility = View.GONE
        } else {
            mBinding.tvRetry.visibility = View.GONE
            mBinding.tvRetryTimer.visibility = View.GONE
            mBinding.btReSend.visibility = View.VISIBLE
        }
    }


    private fun startOtpCountdownTimer(textView: TextView, totalTimeInSeconds: Int, onTimerFinish: () -> Unit): CountDownTimer {
        textView.isEnabled = false // Disable during countdown


        return object : CountDownTimer((totalTimeInSeconds * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                textView.text = String.format("%d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                textView.text = "Resend OTP"
                textView.isEnabled = true
                onTimerFinish()
            }
        }.apply { start() }
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