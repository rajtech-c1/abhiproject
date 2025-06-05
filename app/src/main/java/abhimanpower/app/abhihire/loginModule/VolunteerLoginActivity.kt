package abhimanpower.app.abhihire.loginModule

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.databinding.ActivityVolunteerLoginBinding
import abhimanpower.app.abhihire.loginModule.apiFunctions.LoginViewModel
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import android.util.Log
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VolunteerLoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityVolunteerLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVolunteerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        setupOtpInputs(binding.etOtp1, binding.etOtp2, binding.etOtp3, binding.etOtp4)


        onClickListeners()

    }

    fun onClickListeners()
    {
        binding.btLogin.setOnClickListener {
            val mobileNo = binding.etEmployeeId.text.toString()

            validateMobileNo(mobileNo)
        }
    }

    fun validateMobileNo(mobileNo: String)
    {
        LoginCredentials.userName=mobileNo
        loginViewModel.validateLogin()

        validateMobileNoObserver()
    }

    fun validateMobileNoObserver()
    {
        loginViewModel.getLoginResponse().observe(this){
            Log.e("Test","Response Received : $it")
        }
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

// In your onCreate():

}