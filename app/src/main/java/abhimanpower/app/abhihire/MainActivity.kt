package abhimanpower.app.abhihire

import abhimanpower.app.abhihire.contractorModule.ContractorHomeActivity
import abhimanpower.app.abhihire.databinding.ActivityMainBinding
import abhimanpower.app.abhihire.loginModule.apiFunctions.LoginViewModel
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.CallIntent
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        getUserLoginStatus()
        setVersionName()
    }

    private fun setVersionName() {
        val versionName = try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            packageInfo.versionName
        } catch (e: Exception) {
            "N/A"
        }

        val tvVersionName = findViewById<TextView>(R.id.tvVersionName)

        tvVersionName.text = "App Version $versionName"
    }

    private fun getUserLoginStatus() {
        Handler(Looper.getMainLooper()).postDelayed({

            val loginStatus = AppData.getUserLoginStatus(this)

            if (loginStatus) {

                when (AppData.getUserRole(this)) {
                    1 -> {
                        validateWorkerLogin(AppData.getUserMobileNo(this))
                    }
                    2->{
                        validateVolunteerLogin(AppData.getUserMobileNo(this))
                    }
                    3->{
                        validateContractorLogin(AppData.getUserMobileNo(this))
                    }
                }

            } else {
                CallIntent.gotoRoleSelectionActivity(this, true, this)
            }

        }, 3000)
    }


    //Worker Login
    private fun validateWorkerLogin(mobileNo: String) {
        loginViewModel.resetWorkerLoginResponse()
        loginViewModel.workerLogin(mobileNo)

        loginViewModel.getWorkerLoginResponse().observe(this) {
            when (it.status) {
                200 -> {
                    LoginCredentials.workerAccountData = it.accountData
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
                    CallIntent.gotoVolunteerHomeActivity(this, true, this)
                }
                else -> {
                    UtilFunctions.showToast(this, "No Response")
                }
            }
            Log.e("Test", "Response Received : $it")
        }
    }

    //Contractor Login
    private fun validateContractorLogin(mobileNo: String)
    {
        loginViewModel.resetContractorLoginResponse()
        loginViewModel.contractorLogin(mobileNo)

        loginViewModel.getContractorLoginResponse().observe(this) {
            when (it.status) {
                200 -> {
                    LoginCredentials.contractorAccountData = it.accountData
                    CallIntent.gotoNextActivity(this, true, this,ContractorHomeActivity())
                }

                else -> {
                    UtilFunctions.showToast(this, it.message!!)
                }
            }
        }
    }
}