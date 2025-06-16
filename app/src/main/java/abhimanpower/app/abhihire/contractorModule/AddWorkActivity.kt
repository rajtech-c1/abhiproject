package abhimanpower.app.abhihire.contractorModule

import abhimanpower.app.abhihire.contractorModule.apiFunctions.ContractorApiFunction
import abhimanpower.app.abhihire.contractorModule.apiFunctions.ContractorViewModel
import abhimanpower.app.abhihire.contractorModule.modalClass.AddWorkDataResponse
import abhimanpower.app.abhihire.contractorModule.modalClass.WorkData
import abhimanpower.app.abhihire.contractorModule.uiFunctions.AddWorkUI
import abhimanpower.app.abhihire.databinding.ActivityAddWorkBinding
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddWorkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWorkBinding

    private lateinit var addWorker: AddWorkUI

    private lateinit var contractorViewModel: ContractorViewModel
    private lateinit var contractorApiFunction: ContractorApiFunction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contractorViewModel = ViewModelProvider(this)[ContractorViewModel::class.java]

        initView()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btBack.setOnClickListener {
            finish()
        }
    }

    private fun initView() {
        addWorker = AddWorkUI(this, binding, ::onPostAddWork)

        contractorApiFunction =
            ContractorApiFunction(
                this,
                this,
                contractorViewModel,
                onAddWorkResponse = ::onAddWorkResponse
            )
    }

    private fun onPostAddWork(workData: WorkData) {
        contractorApiFunction.addWork(workData)
    }

    private fun onAddWorkResponse(response: AddWorkDataResponse) {

        when(response.status)
        {
            200 -> {
                UtilFunctions.showToast(this,"Work Added Successfully")
            }
            else -> {
                UtilFunctions.showToast(this,"Server Error")
            }
        }
    }
}