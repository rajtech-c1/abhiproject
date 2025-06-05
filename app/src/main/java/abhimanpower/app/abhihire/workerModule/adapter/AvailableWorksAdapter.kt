package abhimanpower.app.abhihire.workerModule.adapter

import abhimanpower.app.abhihire.databinding.ItemWorkdetailsBinding
import abhimanpower.app.abhihire.workerModule.modalClass.WorkData
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class AvailableWorksAdapter(
    private var context: Context,
    private var availableWorks: List<WorkData>,
    private val onWorkListFetched: (workData: WorkData) -> Unit = {},
) : RecyclerView.Adapter<AvailableWorksAdapter.AvailableWorksViewHolder>() {

    class AvailableWorksViewHolder(var binding: ItemWorkdetailsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AvailableWorksViewHolder {
        return AvailableWorksViewHolder(
            ItemWorkdetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AvailableWorksViewHolder, position: Int) {

        val workData = availableWorks[position]

        holder.binding.tvWorkName.text = workData.workName
        holder.binding.tvPlaceName.text = workData.place
    }

    override fun getItemCount(): Int {
        return availableWorks.size
    }


}