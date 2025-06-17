package abhimanpower.app.abhihire.loginModule

import abhimanpower.app.abhihire.contractorModule.ContractorHomeActivity
import abhimanpower.app.abhihire.contractorModule.RegisterContractorActivity
import abhimanpower.app.abhihire.databinding.ActivityVolunteerLoginBinding
import abhimanpower.app.abhihire.loginModule.apiFunctions.LoginViewModel
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.loginModule.uiFunctions.LoginUI
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.CallIntent
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VolunteerLoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityVolunteerLoginBinding

    private lateinit var loginUI: LoginUI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVolunteerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        setupOtpInputs(binding.etOtp1, binding.etOtp2, binding.etOtp3, binding.etOtp4)

        initView()
        onClickListeners()
    }

    private fun initView() {
        loginUI = LoginUI(this, binding,::onOTPVerified)
    }

    private fun onClickListeners() {

        binding.btSendOTP.setOnClickListener {

            val mobileNo = binding.etMobileNum.text.toString()
            val isTermsAccepted = binding.termsCheckBox.isChecked

            if (mobileNo.length != 10) {
                loginUI.showError(true, "Enter Valid MobileNo")
//                UtilFunctions.showToast(this, "Enter Valid MobileNo")
                return@setOnClickListener
            }

            if (!isTermsAccepted) {
                UtilFunctions.showToast(this, "Accept T&C to continue")
                return@setOnClickListener
            }

            checkUserRoleandLogin(mobileNo)
        }

        binding.btLogin.setOnClickListener {
            loginUI.validateOTP()
        }

        binding.btRegister.setOnClickListener {
            checkUserRoleandRegister()
        }

        binding.btChangeNumber.setOnClickListener {
            loginUI.showNumberScreen()
        }

        binding.btReSend.setOnClickListener {
            loginUI.showTimer(true)
        }
    }

    private fun onOTPVerified(isOTPCorrect: Boolean)
    {
        gotoHomeScreen()
    }

    private fun gotoHomeScreen() {
        when (LoginCredentials.selectedRole) {
            1 -> {
                saveToSP(1, LoginCredentials.workerAccountData.mobileNo)
                CallIntent.gotoWorkerHomeActivity(this, true, this)
            }

            2 -> {
                saveToSP(2, LoginCredentials.volunteerAccountData.mobileNo)
                CallIntent.gotoVolunteerHomeActivity(this, true, this)
            }

            3 -> {
                saveToSP(3, LoginCredentials.contractorAccountData.mobileNo)
                CallIntent.gotoNextActivity(this, true, this, ContractorHomeActivity())
            }
        }
    }

    private fun saveToSP(userRole: Int, mobileNo: String) {
        AppData.putUserLoginStatus(this, true)
        AppData.putUserRole(this, userRole)
        AppData.putUserMobileNo(this, mobileNo)
    }

    private fun checkUserRoleandRegister() {
        when (LoginCredentials.selectedRole) {
            1 -> {
                CallIntent.gotoWorkerRegisterActivity(this, false, this)
            }

            2 -> {
                UtilFunctions.showToast(this, "No Registration for Volunteer")
//                CallIntent.gotoVolunteerHomeActivity(this, false, this)
            }

            3 -> {
                CallIntent.gotoNextActivity(this, false, this, RegisterContractorActivity())
            }

            4 -> {

            }
        }
    }

    private fun checkUserRoleandLogin(mobileNo: String) {
        when (LoginCredentials.selectedRole) {
            1 -> {
                validateWorkerLogin(mobileNo)
            }

            2 -> {
                validateVolunteerMobileNo(mobileNo)
            }

            3 -> {
                validateContractorLogin(mobileNo)
            }

            4 -> {

            }
        }
    }

    private fun validateVolunteerMobileNo(mobileNo: String) {
        LoginCredentials.userName = mobileNo

        loginUI.showPB(true)
        loginViewModel.resetLoginResponse()
        loginViewModel.validateLogin()

        validateMobileNoObserver()
    }

    private fun validateMobileNoObserver() {
        loginViewModel.getLoginResponse().observe(this) {
            when (it.status) {
                200 -> {
                    LoginCredentials.volunteerAccountData = it.accountData
                    UtilFunctions.showToast(this, "Login Successfull")
                    sendOTP(LoginCredentials.volunteerAccountData.mobileNo)
                }

                else -> {
                    UtilFunctions.showToast(this, "Server Error")
                }
            }
            loginUI.showPB(false)
            Log.e("Test", "Response Received : $it")
        }
    }

    private fun validateWorkerLogin(mobileNo: String) {
        loginUI.showPB(true)
        loginViewModel.resetWorkerLoginResponse()
        loginViewModel.workerLogin(mobileNo)

        loginViewModel.getWorkerLoginResponse().observe(this) {
            when (it.status) {
                200 -> {
                    LoginCredentials.workerAccountData = it.accountData
                    LoginCredentials.planDetails=it.plan
                    UtilFunctions.showToast(this, "Login Successfull")
                    sendOTP(LoginCredentials.workerAccountData.mobileNo)
                }

                else -> {
                    UtilFunctions.showToast(this, it.message!!)
                }
            }
            loginUI.showPB(false)
        }
    }

    private fun validateContractorLogin(mobileNo: String) {
        loginUI.showPB(true)
        loginViewModel.resetContractorLoginResponse()
        loginViewModel.contractorLogin(mobileNo)

        loginViewModel.getContractorLoginResponse().observe(this) {
            when (it.status) {
                200 -> {
                    LoginCredentials.contractorAccountData = it.accountData
                    LoginCredentials.planDetails=it.plan
                    UtilFunctions.showToast(this, "Login Successfull")
                    sendOTP(LoginCredentials.contractorAccountData.mobileNo)

                }

                else -> {
                    UtilFunctions.showToast(this, it.message!!)
                }
            }
            loginUI.showPB(false)
        }
    }

    fun sendOTP(number:String)
    {
        //TODO Send OTP Logic

        loginUI.setOTPValue("1234")
        loginUI.showOTPScreen()

    }


    private fun setupOtpInputs(vararg editTexts: EditText) {
        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener {
                if (it?.length == 1 && i < editTexts.lastIndex) {
                    editTexts[i + 1].requestFocus()
                } else if (it?.isEmpty() == true && i > 0) {
                    editTexts[i - 1].requestFocus()
                }
            }
        }
    }


}