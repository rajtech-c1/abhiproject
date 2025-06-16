package abhimanpower.app.abhihire.contractorModule.uiFunctions

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.databinding.ActivityMyWorksBinding
import abhimanpower.app.abhihire.databinding.ActivityWorkerHomeBinding
import abhimanpower.app.abhihire.volunteerModule.modalClass.MonthData
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils


class MyWorksUI(
    context: Context,
    binding: ActivityMyWorksBinding,
    private val onMonthSelected: (monthData: MonthData) -> Unit = {},
) {
    private val mBinding: ActivityMyWorksBinding
    private val mContext: Context

    init {
        mContext = context
        mBinding = binding

        setAnimation()

    }

    fun showList()
    {
        mBinding.rvAvailableWorks.visibility= View.VISIBLE
        mBinding.emptyList.visibility= View.GONE
    }

    fun hideList()
    {
        mBinding.rvAvailableWorks.visibility= View.GONE
        mBinding.emptyList.visibility= View.VISIBLE
    }

    fun showPB(toShown: Boolean)
    {
        if(toShown)
        {
            mBinding.progresslayout.visibility= View.VISIBLE
        }else{
            mBinding.progresslayout.visibility= View.GONE
            mBinding.rvAvailableWorks.visibility= View.VISIBLE
        }
    }

    fun setAnimation()
    {
//        val rotatingCircle = findViewById<ImageView>(R.id.rotatingCircle)
//        val centerIcon = findViewById<ImageView>(R.id.centerIcon)

// Start rotating animation
//        val rotate = AnimationUtils.loadAnimation(mContext, R.anim.rotate_infinite)
//        mBinding.progressCircle.startAnimation(rotate)

// List of icons to show in center
        val icons = listOf(
            R.drawable.loader_1,
            R.drawable.loader_2,
            R.drawable.loader_3,
            R.drawable.loader_4,
            R.drawable.loader_5,
            R.drawable.loader_6
        )

        var currentIcon = 0
        val handler = Handler(Looper.getMainLooper())

        val iconChanger = object : Runnable {
            override fun run() {
                mBinding.centerIcon.setImageResource(icons[currentIcon])
                val fade = AnimationUtils.loadAnimation(mContext, R.anim.fade_in_out)
                mBinding.centerIcon.startAnimation(fade)
                currentIcon = (currentIcon + 1) % icons.size
                handler.postDelayed(this, 1500)
            }
        }
        handler.post(iconChanger)

    }

}