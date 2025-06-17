package abhimanpower.app.abhihire.contractorModule

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.databinding.ActivityContractorProfileBinding
import abhimanpower.app.abhihire.loginModule.LogoutDialog
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.AreaData
import abhimanpower.app.abhihire.zCommonFunctions.CallIntent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import coil.load
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContractorProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContractorProfileBinding

    private lateinit var logoutDialog: LogoutDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContractorProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logoutDialog = LogoutDialog(layoutInflater, this, ::onLogoutClicked)


        setOnClickListeners()
    }


    override fun onResume() {
        super.onResume()

        setData()
        setImage()
    }

    private fun setData() {
        if (AppData.getUserRole(this) == 3) {
            val workerAccountData = LoginCredentials.contractorAccountData
            Log.e("Test", "Contractor Account Details : $workerAccountData")

            binding.tvMobileNo.text = workerAccountData.mobileNo
            binding.tvFullName.text = workerAccountData.name
            binding.tvEmail.text = workerAccountData.email
//        binding.tvDOB.text = workerAccountData.dob

//        if (workerAccountData.gender.toInt() == 1)
//            binding.tvGender.text = "Male"
//        else
//            binding.tvGenderTitle.text = "FeMale"

            binding.tvStreet.text = workerAccountData.street
            binding.tvArea.text = workerAccountData.area
            binding.tvPincode.text = workerAccountData.pincode
            binding.tvState.text = AreaData.getState(workerAccountData.state.toInt()-1)
            binding.tvDistrict.text = AreaData.getDistrict(workerAccountData.district.toInt())

        } else {
            Log.e("Test", "Profile of General User")
        }

    }

    private fun setImage() {
        Log.e("Test", "Image Loading : ${LoginCredentials.contractorAccountData.image}")
        binding.profilePic.load(LoginCredentials.contractorAccountData.image) {
            // Optional: Add a placeholder image while loading
            placeholder(R.drawable.ic_add_photo) // Make sure you have this drawable
            // Optional: Add an error image if loading fails
//            error(R.drawable.error_image) // Make sure you have this drawable
            // Optional: Crossfade animation
            crossfade(true)
            crossfade(1000)
//            crossfadeDuration(1000) // 1 second
            // Optional: Transformations (e.g., centerCrop, circleCrop)
            // transformations(CircleCropTransformation())
        }
    }

    private fun onLogoutClicked() {
        AppData.putUserLoginStatus(this, false)
        CallIntent.gotoRoleSelectionActivity(this, true, this)
    }

    private fun setOnClickListeners() {
        binding.btBack.setOnClickListener {
            finish()
        }

        binding.btEditProfile.setOnClickListener {
            CallIntent.gotoNextActivity(this, false, this, EditContractorProfileActivity())
//            CallIntent.gotoEditProfileActivity(this, false, this)
        }

        binding.btLogout.setOnClickListener {
            logoutDialog.openLogoutDialog()
        }
    }
}