package abhimanpower.app.abhihire.workerModule

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.databinding.ActivityWorkerProfileBinding
import abhimanpower.app.abhihire.loginModule.LogoutDialog
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.CallIntent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import coil.load
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkerProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkerProfileBinding

    private lateinit var logoutDialog: LogoutDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logoutDialog = LogoutDialog(layoutInflater, this, ::onLogoutClicked)


//        setData()
//        setImage()
        setOnClickListeners()
    }

    override fun onResume() {
        super.onResume()

        setData()
        setImage()
    }

    private fun setData() {
        val workerAccountData = LoginCredentials.workerAccountData
        Log.e("Test","Worker Account Details : $workerAccountData")

        binding.tvMobileNo.text = workerAccountData.mobileNo
        binding.tvFullName.text = workerAccountData.name
        binding.tvDOB.text = workerAccountData.dob

        if (workerAccountData.gender.toInt() == 1)
            binding.tvGender.text = "Male"
        else
            binding.tvGenderTitle.text = "FeMale"

        binding.tvStreet.text = workerAccountData.street
        binding.tvArea.text = workerAccountData.area
        binding.tvPincode.text = workerAccountData.pincode
        binding.tvState.text = workerAccountData.state

        binding.tvWorkCategory.text =
            AppData.workCategories[workerAccountData.workCategory.toInt()].categoryName
        binding.tvWorkExperience.text = workerAccountData.experience

    }

    private fun setImage()
    {
        Log.e("Test","Image Loading : ${LoginCredentials.workerAccountData.image}")
        binding.profilePic.load(LoginCredentials.workerAccountData.image) {
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
        CallIntent.gotoLoginActivity(this, true, this)
    }

    private fun setOnClickListeners() {
        binding.btBack.setOnClickListener {
            finish()
        }

        binding.btEditProfile.setOnClickListener {
            CallIntent.gotoEditProfileActivity(this, false, this)
        }

        binding.btLogout.setOnClickListener {
            logoutDialog.openLogoutDialog()
        }
    }
}