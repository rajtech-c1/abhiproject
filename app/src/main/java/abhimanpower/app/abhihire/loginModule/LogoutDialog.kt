package abhimanpower.app.abhihire.loginModule

import abhimanpower.app.abhihire.R
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView

class LogoutDialog(
    layoutInflater: LayoutInflater,
    context: Context,
    private val listener: () -> Unit
) {

    private val mLayoutInflater: LayoutInflater
    private val mContext: Context

    private lateinit var dialog: AlertDialog.Builder
    private lateinit var messageBoxInstance: AlertDialog

    init {
        mLayoutInflater = layoutInflater
        mContext = context

        initViews()
    }


    fun initViews() {
        val view = mLayoutInflater.inflate(R.layout.logout_dialog, null)
        dialog = AlertDialog.Builder(mContext, R.style.CurvedDialog).setView(view)
        dialog.setCancelable(false)
        messageBoxInstance = dialog.create()

        val btYes = view.findViewById<TextView>(R.id.btYes)
        val btNo = view.findViewById<TextView>(R.id.btNo)

        btNo.setOnClickListener {
            messageBoxInstance.dismiss()
        }

        btYes.setOnClickListener {
            listener.invoke()
        }
    }

    fun openLogoutDialog() {
        messageBoxInstance.show()
    }

}