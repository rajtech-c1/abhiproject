package abhimanpower.app.abhihire.volunteerModule

import abhimanpower.app.abhihire.databinding.ActivityVolunteerHomeBinding
import abhimanpower.app.abhihire.loginModule.LogoutDialog
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.volunteerModule.adapters.DayInfoAdapter
import abhimanpower.app.abhihire.volunteerModule.apiFunctions.VolunteerApiFunctions
import abhimanpower.app.abhihire.volunteerModule.apiFunctions.VolunteerViewModule
import abhimanpower.app.abhihire.volunteerModule.modalClass.DayData
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetMonthlyStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.GetOverallStatsResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.MonthData
import abhimanpower.app.abhihire.volunteerModule.modalClass.VolunteerData
import abhimanpower.app.abhihire.volunteerModule.uiFuntion.LastRowMarginDecoration
import abhimanpower.app.abhihire.volunteerModule.uiFuntion.VolunteerHomeUI
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.CallIntent
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VolunteerHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVolunteerHomeBinding

    private lateinit var volunteerHomeUI: VolunteerHomeUI
    private lateinit var volunteerApiFunctions: VolunteerApiFunctions
    private lateinit var volunteerViewModule: VolunteerViewModule

    private lateinit var logoutDialog: LogoutDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVolunteerHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        volunteerViewModule = ViewModelProvider(this)[VolunteerViewModule::class.java]


        setData()
//        initView()
        onClickListeners()
    }

    override fun onResume() {
        super.onResume()

        initView()
    }

    private fun setData() {
        binding.tvVolunteerName.text = LoginCredentials.volunteerAccountData.name
    }

    private fun initView() {
        logoutDialog = LogoutDialog(layoutInflater, this, ::onLogoutClicked)

        volunteerHomeUI = VolunteerHomeUI(this, binding, ::onMonthSelected)
        volunteerApiFunctions =
            VolunteerApiFunctions(
                this,
                this,
                volunteerViewModule,
                onAddWorkerResponse = {},
                ::onGetOverAllStatsResponseFetched,
                ::onGetMonthlyStatsResponse
            )

        fetchOverallVolunteerStats()
    }

    private fun onLogoutClicked() {
        AppData.putUserLoginStatus(this, false)
        CallIntent.gotoRoleSelectionActivity(this, true, this)
    }


    private fun onClickListeners() {
        binding.btAddWorker.setOnClickListener {
            CallIntent.gotoAddWorkerActivity(this, false, this)
        }

        binding.btAddContractor.setOnClickListener {
            CallIntent.gotoAddContractorActivity(this, false, this)
        }

        binding.btLogout.setOnClickListener {
            logoutDialog.openLogoutDialog()
        }

        binding.btTranslate.setOnClickListener {
            UtilFunctions.showLanguageSelectionDialog(this, this)
        }
    }

    private fun fetchOverallVolunteerStats() {
        volunteerApiFunctions.getOverallStats(VolunteerData.volunteerId)
    }

    private fun onGetOverAllStatsResponseFetched(response: GetOverallStatsResponse) {
        if (response.status == 200) {
            binding.tvTotalWorkers.text = response.workers!!.totalCount.toString()
            binding.tvWorkerToday.text = response.workers.todayCount.toString()
            binding.tvWorkerWeek.text = response.workers.weekCount.toString()

            binding.tvTotalContractors.text = response.contractors!!.totalCount.toString()
            binding.tvContractorsWeek.text = response.contractors.weekCount.toString()
            binding.tvContractorTodayCount.text = response.contractors.todayCount.toString()
        } else {
            UtilFunctions.showToast(this, "Server Error")
        }
    }

    private fun onMonthSelected(monthData: MonthData) {
        volunteerApiFunctions.getMonthlyStats(monthData.monthName, VolunteerData.volunteerId)
    }

    private fun onGetMonthlyStatsResponse(response: GetMonthlyStatsResponse) {

        if (response.status == 200) {
            volunteerHomeUI.setTotalMonthCount(response.totalCount.toString())
            initDayWiseAdapter(response.daysList)
        } else {
            UtilFunctions.showToast(this, "Server Error")
        }
    }

    private lateinit var dayInfoAdapter: DayInfoAdapter

    private fun initDayWiseAdapter(daysList: ArrayList<DayData>) {

        dayInfoAdapter = DayInfoAdapter(this, daysList)

        binding.volunteerRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(LastRowMarginDecoration(3, 16))

            adapter = dayInfoAdapter
        }
    }
}