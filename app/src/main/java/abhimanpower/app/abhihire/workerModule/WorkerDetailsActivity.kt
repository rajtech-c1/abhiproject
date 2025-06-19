package abhimanpower.app.abhihire.workerModule

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.databinding.ActivityWorkerDetailsBinding
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.workerModule.apiFunctions.WorkerApiFunction
import abhimanpower.app.abhihire.workerModule.apiFunctions.WorkerViewModel
import abhimanpower.app.abhihire.workerModule.modalClass.GetAvailableWorksResponse
import abhimanpower.app.abhihire.workerModule.modalClass.SelectedData
import abhimanpower.app.abhihire.workerModule.modalClass.SelectedData.workData
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.AreaData
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkerDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkerDetailsBinding

    private lateinit var workerViewModel: WorkerViewModel
    private lateinit var workerApiFunction: WorkerApiFunction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        workerViewModel = ViewModelProvider(this)[WorkerViewModel::class.java]

        if (LoginCredentials.selectedRole == 3) {
            initView()
        }

        setData()
        onClickListeners()
    }

    fun initView() {
        workerApiFunction =
            WorkerApiFunction(
                this,
                this,
                workerViewModel,
                onPostDeleted = ::onPostDeleted
            )

        binding.btDeletePost.visibility = View.VISIBLE
    }

    private fun setData() {
        val selectedWorkData = SelectedData.workData

        binding.tvWorkName.text = selectedWorkData.workName

        binding.tvDatePosted.text =
            "Work Category : ${AppData.workCategories[selectedWorkData.workCategory.toInt()].categoryName}"

        binding.tvDescription.text = selectedWorkData.description

        binding.tvMobileValue.text = "${UtilFunctions.formatDateToMonthDay(workData.datePosted)}"

        binding.tvAreaValue.text = "${selectedWorkData.place}"
        binding.tvDistrictValue.text =
            "${AreaData.getDistrict(workData.district.toInt(), workData.state.toInt())}"

        binding.tvStateValue.text =
            AreaData.getState(workData.state.toInt())

        binding.tvPincodeValue.text = workData.pincode

        //Client Details
        if (LoginCredentials.selectedRole == 1)
            setClientDetails()
        else {
            binding.ltClientDetails.visibility = View.GONE
            binding.ltClientDetailsMain.visibility = View.GONE
        }
    }

    private fun setClientDetails() {
        val selectedWorkData = SelectedData.workData

        if (selectedWorkData.image.isNotEmpty()) {
            Log.e("Test", "Image Loading : ${LoginCredentials.workerAccountData.image}")
            binding.ivUserPic.load(LoginCredentials.workerAccountData.image) {
                placeholder(R.drawable.ic_add_photo) // Make sure you have this drawable
                crossfade(true)
                crossfade(1000)
            }
        } else {
            binding.ivUserPic.setImageResource(R.drawable.ic_shop_owner)
        }

        binding.tvUserName.text = selectedWorkData.userName
        binding.tvUserPlaceName.text = AreaData.getDistrictState(
            selectedWorkData.userDistrict.toInt(),
            selectedWorkData.userState.toInt()
        )
        binding.tvPhonenumber.text = selectedWorkData.userContact
    }

    private fun onClickListeners() {
        binding.btBack.setOnClickListener {
            finish()
        }

        binding.btCallNow.setOnClickListener {
            UtilFunctions.openDialer(this, SelectedData.workData.userContact)
        }

        binding.btDeletePost.setOnClickListener {
            workerApiFunction.deletePost(SelectedData.workData.workId.toInt())
        }
    }

    private fun onPostDeleted(response: GetAvailableWorksResponse) {

        if (response.status == 200) {
            UtilFunctions.showToast(this, "Post Deleted Successfully")
            finish()
        } else {
            UtilFunctions.showToast(this, "Failed to delete the post")

        }
    }
}