package abhimanpower.app.abhihire.volunteerModule.uiFuntion

import abhimanpower.app.abhihire.databinding.ActivityVolunteerHomeBinding
import abhimanpower.app.abhihire.volunteerModule.adapters.MonthAdapter
import abhimanpower.app.abhihire.volunteerModule.modalClass.MonthData
import android.content.Context
import android.view.View
import android.widget.AdapterView


class VolunteerHomeUI(
    context: Context,
    binding: ActivityVolunteerHomeBinding,
    private val onMonthSelected: (monthData: MonthData) -> Unit,
) {
    private val mBinding: ActivityVolunteerHomeBinding
    private val mContext: Context

    init {
        mContext = context
        mBinding = binding

        initMonthsSpinner()
    }


    private var selectedMonth = MonthData()

    private fun initMonthsSpinner(
    ) {

        val allLastSixMonths = getLastSixMonths()

        val adapter = MonthAdapter(
            mContext,
            android.R.layout.simple_spinner_item,
            allLastSixMonths
        )

        mBinding.spSelectMonth.adapter = adapter
        mBinding.spSelectMonth.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedMonth = allLastSixMonths[position]


                    if (position != 0) {
                        onMonthSelected.invoke(
                            selectedMonth
                        )
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle case when nothing is selected (optional)
                }
            }
    }

    fun getLastSixMonths(): List<MonthData> {
        val monthList = mutableListOf<MonthData>()
        val currentDate = java.util.Calendar.getInstance()

        // Add "Select Month" as the first option
        monthList.add(MonthData(0, "Select Month"))

        // Loop to get last 6 months
        for (i in 0 until 6) {
            val monthNumber =
                currentDate.get(java.util.Calendar.MONTH) + 1 // Calendar.MONTH is 0-based
            val monthName = java.text.DateFormatSymbols().months[monthNumber - 1]

            monthList.add(MonthData(monthNumber, monthName))

            // Go back one month
            currentDate.add(java.util.Calendar.MONTH, -1)
        }

        return monthList
    }
}