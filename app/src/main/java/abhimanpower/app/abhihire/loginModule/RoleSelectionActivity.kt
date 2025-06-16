package abhimanpower.app.abhihire.loginModule

import abhimanpower.app.abhihire.contractorModule.ContractorHomeActivity
import abhimanpower.app.abhihire.databinding.ActivityRoleSelectionBinding
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.zCommonFunctions.CallIntent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RoleSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoleSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoleSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btVolunteerLogin.setOnClickListener {
            LoginCredentials.selectedRole = 2
            CallIntent.gotoLoginActivity(this, true, this)
        }

        binding.btWorkerLogin.setOnClickListener {
            LoginCredentials.selectedRole = 1
            CallIntent.gotoLoginActivity(this, true, this)
        }

        binding.btContractorLogin.setOnClickListener {
            LoginCredentials.selectedRole = 3
            CallIntent.gotoNextActivity(this, true, this,VolunteerLoginActivity())
        }


    }
}