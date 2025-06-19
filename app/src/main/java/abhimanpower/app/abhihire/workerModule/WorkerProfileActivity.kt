package abhimanpower.app.abhihire.workerModule

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.databinding.ActivityWorkerProfileBinding
import abhimanpower.app.abhihire.loginModule.LogoutDialog
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.AreaData
import abhimanpower.app.abhihire.zCommonFunctions.CallIntent
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
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
        binding.tvState.text = AreaData.getState(workerAccountData.state.toInt()-1)
        binding.tvDistrict.text = AreaData.getDistrict(workerAccountData.district.toInt(),workerAccountData.state.toInt())

        binding.tvWorkCategory.text =
            AppData.workCategories[workerAccountData.workCategory.toInt()-1].categoryName
        binding.tvWorkExperience.text = workerAccountData.experience

    }

    private fun setImage()
    {
        if(LoginCredentials.workerAccountData.image.isNotEmpty()) {
            Log.e("Test", "Image Loading : ${LoginCredentials.workerAccountData.image}")
            binding.profilePic.load(LoginCredentials.workerAccountData.image) {
                placeholder(R.drawable.ic_add_photo) // Make sure you have this drawable
                crossfade(true)
                crossfade(1000)
            }
        }else{
            binding.profilePic.setImageResource(R.drawable.ic_shop_owner)
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
            CallIntent.gotoEditProfileActivity(this, false, this)
        }

        binding.btLogout.setOnClickListener {
            logoutDialog.openLogoutDialog()
        }

        binding.btTranslate.setOnClickListener {
            UtilFunctions.showLanguageSelectionDialog(this, this)
        }
    }
}