package abhimanpower.app.abhihire.workerModule

import abhimanpower.app.abhihire.databinding.ActivityWorkerDetailsBinding
import abhimanpower.app.abhihire.workerModule.modalClass.SelectedData
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkerDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkerDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()
        onClickListeners()
    }

    private fun setData() {
        val selectedWorkData = SelectedData.workData

        binding.tvWorkName.text = selectedWorkData.workName
        binding.tvPlaceName.text = selectedWorkData.place

        binding.tvDescription.text = selectedWorkData.description

        binding.tvDatePosted.text = UtilFunctions.formatDateToMonthDay(selectedWorkData.datePosted)

    }

    private fun onClickListeners() {
        binding.btBack.setOnClickListener {
            finish()
        }
    }
}