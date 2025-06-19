package abhimanpower.app.abhihire.loginModule

import abhimanpower.app.abhihire.contractorModule.ContractorHomeActivity
import abhimanpower.app.abhihire.databinding.ActivityMpinBinding
import abhimanpower.app.abhihire.loginModule.apiFunctions.LoginViewModel
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.CallIntent
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MPinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMpinBinding
    private var isNewMpinSetup = false

    private lateinit var loginViewModel: LoginViewModel

    companion object {
        private const val MAX_MPIN_ATTEMPTS = 5
        private const val MPIN_LOCKOUT_TIME_MS = 5 * 60 * 1000 // 5 minutes
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMpinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        // Check if MPIN already exists
        isNewMpinSetup = !isMpinSet()

        Log.e("Test", "Mpin States : ${isMpinSet()}")

        if (isMpinSet()) {
            binding.tvOTPTitle.text = "Enter MPIN"
        } else {
            binding.tvOTPTitle.text = "Setup MPIN"

            binding.tvTitleResetMpin.visibility = View.GONE
            binding.ltReset.visibility = View.GONE
        }

        setupUI()

        checkMpinLockoutStatus()
        setupOtpFields()
        setupClickListeners()
    }

    private fun checkMpinLockoutStatus() {
        val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val lastAttemptTime = prefs.getLong("last_mpin_attempt_time", 0)
        val attemptCount = prefs.getInt("mpin_attempt_count", 0)

        if (attemptCount >= MAX_MPIN_ATTEMPTS &&
            System.currentTimeMillis() - lastAttemptTime < MPIN_LOCKOUT_TIME_MS
        ) {

            val remainingTime = (MPIN_LOCKOUT_TIME_MS -
                    (System.currentTimeMillis() - lastAttemptTime)) / 1000
            showLockoutMessage(remainingTime)
            disableMpinInput()
        }
    }

    private fun showLockoutMessage(remainingSeconds: Long) {
        val minutes = remainingSeconds / 60
        val seconds = remainingSeconds % 60
        Toast.makeText(
            this,
            "Too many attempts. Try again in $minutes:${String.format("%02d", seconds)}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun disableMpinInput() {
        binding.etOtp1.isEnabled = false
        binding.etOtp2.isEnabled = false
        binding.etOtp3.isEnabled = false
        binding.etOtp4.isEnabled = false
        binding.btReset.isEnabled = false

        // Re-enable after lockout period
        binding.root.postDelayed({
            resetMpin()
            enableMpinInput()
        }, MPIN_LOCKOUT_TIME_MS.toLong())
    }

    private fun enableMpinInput() {
        binding.etOtp1.isEnabled = true
        binding.etOtp2.isEnabled = true
        binding.etOtp3.isEnabled = true
        binding.etOtp4.isEnabled = true
        binding.btReset.isEnabled = true
        binding.etOtp1.requestFocus()
    }

    /**
     * Records MPIN attempt (success or failure)
     */
    private fun recordMpinAttempt(isSuccessful: Boolean) {
        val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val editor = prefs.edit()

        if (isSuccessful) {
            editor.putInt("mpin_attempt_count", 0)
        } else {
            val attemptCount = prefs.getInt("mpin_attempt_count", 0) + 1
            editor.putInt("mpin_attempt_count", attemptCount)
                .putLong("last_mpin_attempt_time", System.currentTimeMillis())

            if (attemptCount >= MAX_MPIN_ATTEMPTS) {
                showLockoutMessage(MPIN_LOCKOUT_TIME_MS.toLong() / 1000)
                disableMpinInput()
            }
        }
        editor.apply()
    }

    /**
     * Enhanced reset function with security checks
     */
    private fun resetMpin(): Boolean {
        return try {
            getSharedPreferences("AppPrefs", MODE_PRIVATE).edit()
                .remove("user_mpin")
                .putInt("mpin_attempt_count", 0) // Reset attempt counter
                .remove("last_mpin_attempt_time")
                .apply()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Modify existing functions:

    private fun verifyOrSaveMpin() {
        val mpin = getEnteredMpin()

        if (mpin.length != 4) {
            setError(true, "Please enter complete 4-digit MPIN")
            return
        }

        if (isNewMpinSetup) {
            saveMpin(mpin)
            recordMpinAttempt(true) // Successful setup
            Toast.makeText(this, "MPIN set successfully", Toast.LENGTH_SHORT).show()
//            navigateToHome()
            gotoHomeActivity()
        } else {
            if (verifyMpin(mpin)) {
                recordMpinAttempt(true) // Successful verification
                navigateToHome()
            } else {
                recordMpinAttempt(false) // Failed attempt
                clearOtpFields()
                setError(true, "Incorrect MPIN")
            }
        }
    }

    private fun setupClickListeners() {
        binding.btReset.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Reset MPIN")
                .setMessage("This will require you to login again. Continue?")
                .setPositiveButton("Reset") { _, _ ->
                    if (resetMpin()) {
                        Toast.makeText(this, "MPIN reset successfully", Toast.LENGTH_SHORT).show()
                        // Navigate to login screen
                        gotoRoleSelectionAScreen()


                    } else {
                        Toast.makeText(this, "Failed to reset MPIN", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }


    //Old Code

    private fun gotoHomeActivity() {

        Log.e("Test","Coming from Login :  ${LoginCredentials.selectedRole}")

        when (LoginCredentials.selectedRole) {
            1 -> {
                saveToSP(LoginCredentials.selectedRole, LoginCredentials.workerAccountData.mobileNo)
                CallIntent.gotoWorkerHomeActivity(this, true, this)
            }

            2 -> {
                saveToSP(
                    LoginCredentials.selectedRole,
                    LoginCredentials.volunteerAccountData.mobileNo
                )
                CallIntent.gotoVolunteerHomeActivity(this, true, this)
            }

            3 -> {
                saveToSP(
                    LoginCredentials.selectedRole,
                    LoginCredentials.contractorAccountData.mobileNo
                )
                CallIntent.gotoNextActivity(this, true, this, ContractorHomeActivity())
            }
        }

    }

    private fun getUserLoginStatus() {

        Log.e("Test","App opened directly :  ${AppData.getUserRole(this)}")

        when (AppData.getUserRole(this)) {
            1 -> {
                validateWorkerLogin(AppData.getUserMobileNo(this))
            }

            2 -> {
                validateVolunteerLogin(AppData.getUserMobileNo(this))
            }

            3 -> {
                validateContractorLogin(AppData.getUserMobileNo(this))
            }
        }


    }


    //Worker Login
    private fun validateWorkerLogin(mobileNo: String) {
        loginViewModel.resetWorkerLoginResponse()
        loginViewModel.workerLogin(mobileNo)

        loginViewModel.getWorkerLoginResponse().observe(this) {
            when (it.status) {
                200 -> {
                    LoginCredentials.workerAccountData = it.accountData
                    LoginCredentials.planDetails = it.plan
//                    saveToSP(
//                        LoginCredentials.selectedRole,
//                        LoginCredentials.workerAccountData.mobileNo
//                    )

                    CallIntent.gotoWorkerHomeActivity(this, true, this)
                }

                else -> {
                    UtilFunctions.showToast(this, it.message!!)
                }
            }
        }
    }


    //Volunteer Login
    private fun validateVolunteerLogin(mobileNo: String) {
        LoginCredentials.userName = mobileNo

        loginViewModel.resetLoginResponse()
        loginViewModel.validateLogin()

        loginViewModel.getLoginResponse().observe(this) {
            when (it.status) {
                200 -> {
                    LoginCredentials.volunteerAccountData = it.accountData
//                    saveToSP(
//                        LoginCredentials.selectedRole,
//                        LoginCredentials.volunteerAccountData.mobileNo
//                    )

                    CallIntent.gotoVolunteerHomeActivity(this, true, this)
                }

                201 -> {
                    UtilFunctions.showToast(this, "Invalid Credentails")
                }

                else -> {
                    UtilFunctions.showToast(this, "Server Error")
                }
            }
            Log.e("Test", "Response Received : $it")
        }
    }

    //Contractor Login
    private fun validateContractorLogin(mobileNo: String) {
        loginViewModel.resetContractorLoginResponse()
        loginViewModel.contractorLogin(mobileNo)

        loginViewModel.getContractorLoginResponse().observe(this) {
            when (it.status) {
                200 -> {
                    LoginCredentials.contractorAccountData = it.accountData
                    LoginCredentials.planDetails = it.plan
//                    saveToSP(
//                        LoginCredentials.selectedRole,
//                        LoginCredentials.contractorAccountData.mobileNo
//                    )
                    CallIntent.gotoNextActivity(this, true, this, ContractorHomeActivity())
                }

                else -> {
                    UtilFunctions.showToast(this, it.message!!)
                }
            }
        }
    }

    private fun saveToSP(userRole: Int, mobileNo: String) {
        AppData.putUserLoginStatus(this, true)
        AppData.putUserRole(this, userRole)
        AppData.putUserMobileNo(this, mobileNo)
    }

    //Set MPIN

    private fun isMpinSet(): Boolean {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        return sharedPref.getString("user_mpin", null) != null
    }

    private fun setupUI() {
        if (isNewMpinSetup) {
            binding.tvOTPTitle.text = "Set Your MPIN"
            binding.tvTitleResetMpin.visibility = View.GONE
            binding.ltReset.visibility = View.GONE
        } else {
            binding.tvOTPTitle.text = "Enter MPIN"
            binding.tvTitleResetMpin.visibility = View.VISIBLE
            binding.ltReset.visibility = View.VISIBLE
        }
    }

    private fun setupOtpFields() {
        val otpFields = listOf(
            binding.etOtp1,
            binding.etOtp2,
            binding.etOtp3,
            binding.etOtp4
        )

        otpFields.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        if (index < 3) {
                            otpFields[index + 1].requestFocus()
                        } else {
                            verifyOrSaveMpin()
                        }

                        if (s.length == 1) {
                            setError(false, "")
                        }
                    } else if (s?.isEmpty() == true && index > 0) {
                        otpFields[index - 1].requestFocus()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }


    private fun getEnteredMpin(): String {
        return binding.etOtp1.text.toString() +
                binding.etOtp2.text.toString() +
                binding.etOtp3.text.toString() +
                binding.etOtp4.text.toString()
    }

    private fun clearOtpFields() {
        binding.etOtp1.text?.clear()
        binding.etOtp2.text?.clear()
        binding.etOtp3.text?.clear()
        binding.etOtp4.text?.clear()
        binding.etOtp1.requestFocus()

        setError(false, "")
    }

    private fun saveMpin(mpin: String) {
        getSharedPreferences("AppPrefs", MODE_PRIVATE).edit()
            .putString("user_mpin", mpin)
            .apply()
    }

    private fun verifyMpin(mpin: String): Boolean {
        val savedMpin = getSharedPreferences("AppPrefs", MODE_PRIVATE)
            .getString("user_mpin", null)
        return mpin == savedMpin
    }


    private fun navigateToHome() {
        getUserLoginStatus()
    }

    private fun gotoRoleSelectionAScreen() {
        AppData.putUserLoginStatus(this, false)
        CallIntent.gotoNextActivity(this, true, this, RoleSelectionActivity())
    }

    private fun setError(showError: Boolean, message: String) {
        if (showError) {
            binding.tvError.text = message
            binding.tvError.visibility = View.VISIBLE
        } else {
            binding.tvError.visibility = View.GONE
        }
    }

}