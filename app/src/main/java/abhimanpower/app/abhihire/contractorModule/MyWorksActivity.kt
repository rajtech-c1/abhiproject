package abhimanpower.app.abhihire.contractorModule

import abhimanpower.app.abhihire.contractorModule.uiFunctions.MyWorksUI
import abhimanpower.app.abhihire.databinding.ActivityMyWorksBinding
import abhimanpower.app.abhihire.workerModule.adapter.AvailableWorksAdapter
import abhimanpower.app.abhihire.workerModule.apiFunctions.WorkerApiFunction
import abhimanpower.app.abhihire.workerModule.apiFunctions.WorkerViewModel
import abhimanpower.app.abhihire.workerModule.modalClass.GetAvailableWorksResponse
import abhimanpower.app.abhihire.workerModule.modalClass.SelectedData
import abhimanpower.app.abhihire.workerModule.modalClass.WorkData
import abhimanpower.app.abhihire.zCommonFunctions.CallIntent
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyWorksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyWorksBinding

    private lateinit var workerViewModel: WorkerViewModel

    private lateinit var workerApiFunction: WorkerApiFunction

    private lateinit var availableWorksAdapter: AvailableWorksAdapter


    private lateinit var myWorksUI: MyWorksUI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyWorksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        workerViewModel = ViewModelProvider(this)[WorkerViewModel::class.java]


        initView()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btSubmit.setOnClickListener {
            CallIntent.gotoAddWorkActivity(this, false, this)
        }
        binding.btBack.setOnClickListener {
            finish()
        }
    }

    private fun initView() {

        workerApiFunction =
            WorkerApiFunction(
                this,
                this,
                workerViewModel,
                ::onGetAvailableWorksResponseFetched
            )

        myWorksUI = MyWorksUI(this, binding)

        getAvailableWorks()
    }

    private fun getAvailableWorks() {
        myWorksUI.showPB(true)
        workerApiFunction.getAvailableWorks()
    }

    private fun onGetAvailableWorksResponseFetched(response: GetAvailableWorksResponse) {
        when (response.status) {
            200 -> {
                myWorksUI.showPB(false)
                if (response.worksList.isNotEmpty()) {
                    orgWorkList = response.worksList
                    initAvailableWorks(response.worksList)
                } else
                    UtilFunctions.showToast(this, "No Works Are Available")
            }

            else -> {
                UtilFunctions.showToast(this, "Server Error")
            }
        }
    }

    private lateinit var orgWorkList: ArrayList<WorkData>

    private fun initAvailableWorks(worksList: ArrayList<WorkData>) {

        if (worksList.isEmpty())
            myWorksUI.hideList()

        availableWorksAdapter = AvailableWorksAdapter(this, worksList, ::onWorkClicked)

        binding.rvAvailableWorks.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )
            adapter = availableWorksAdapter
        }

        binding.rvAvailableWorks.isNestedScrollingEnabled = false
    }

    private fun onWorkClicked(workData: WorkData) {
        SelectedData.workData = workData
        CallIntent.gotoWorkDetailsActivity(this, false, this)
    }

}