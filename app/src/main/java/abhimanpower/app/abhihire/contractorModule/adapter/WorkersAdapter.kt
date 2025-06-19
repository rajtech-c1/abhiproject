package abhimanpower.app.abhihire.contractorModule.adapter

import abhimanpower.app.abhihire.databinding.ItemWorkerBinding
import abhimanpower.app.abhihire.workerModule.modalClass.SelectedData.workData
import abhimanpower.app.abhihire.workerModule.modalClass.WorkerAccountData
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.AreaData
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class WorkersAdapter(
    private var context: Context,
    private var availableWorkers: List<WorkerAccountData>,
    private val onWorkerViewClicked: (workerAccount: WorkerAccountData) -> Unit = {},
) : RecyclerView.Adapter<WorkersAdapter.WorkersAdapterViewHolder>() {

    class WorkersAdapterViewHolder(var binding: ItemWorkerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkersAdapterViewHolder {
        return WorkersAdapterViewHolder(
            ItemWorkerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WorkersAdapterViewHolder, position: Int) {

        val workerData = availableWorkers[position]

        Log.e("Test","Item[$position] - $workerData")

        holder.binding.tvWorkName.text = workerData.name
        holder.binding.tvPlaceName.text =
            AreaData.getDistrictState(workerData.district.toInt(),workerData.state.toInt())

        holder.binding.tvDatePosted.text =
            "Experience : ${workerData.experience} Years"

        holder.binding.btView.setOnClickListener {
            onWorkerViewClicked.invoke(workerData)
        }
    }

    override fun getItemCount(): Int {
        return availableWorkers.size
    }


}