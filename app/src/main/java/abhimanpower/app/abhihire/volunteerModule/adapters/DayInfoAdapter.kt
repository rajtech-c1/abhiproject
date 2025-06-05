package abhimanpower.app.abhihire.volunteerModule.adapters

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.databinding.ItemDaywiseBinding
import abhimanpower.app.abhihire.volunteerModule.modalClass.DayData
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale


class DayInfoAdapter(
    private var context: Context,
    private var daysList: List<DayData>,
    private val onStudentList: (dayData: DayData) -> Unit = {},
) : RecyclerView.Adapter<DayInfoAdapter.DayInfoAdapterViewHolder>() {

    class DayInfoAdapterViewHolder(var binding: ItemDaywiseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DayInfoAdapterViewHolder {
        return DayInfoAdapterViewHolder(
            ItemDaywiseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DayInfoAdapterViewHolder, position: Int) {

        val day = daysList[position]

        holder.binding.dayTitle.text = formatDateToMonthDay(day.date)
        holder.binding.totalCount.text = day.count.toString()


    }

    override fun getItemCount(): Int {
        return daysList.size
    }

    private fun formatDateToMonthDay(inputDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM d", Locale.getDefault()) // e.g., May 1
            val date = inputFormat.parse(inputDate)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            ""
        }
    }

}