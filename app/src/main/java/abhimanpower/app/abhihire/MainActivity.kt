package abhimanpower.app.abhihire

import abhimanpower.app.abhihire.databinding.ActivityMainBinding
import abhimanpower.app.abhihire.loginModule.MPinActivity
import abhimanpower.app.abhihire.loginModule.RoleSelectionActivity
import abhimanpower.app.abhihire.loginModule.VolunteerLoginActivity
import abhimanpower.app.abhihire.loginModule.apiFunctions.LoginViewModel
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.CallIntent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        checkUserLoginStatus()
        setVersionName()
    }

    private fun checkUserLoginStatus() {

        Handler(Looper.getMainLooper()).postDelayed({

            val loginStatus = AppData.getUserLoginStatus(this)

            if (loginStatus) {
                CallIntent.gotoNextActivity(this, true, this, MPinActivity())
            } else {
                CallIntent.gotoNextActivity(this, true, this, RoleSelectionActivity())
            }
        }, 1000)
    }


    private fun setVersionName() {
        val versionName = try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            packageInfo.versionName
        } catch (e: Exception) {
            "N/A"
        }

        val tvVersionName = findViewById<TextView>(R.id.tvVersionName)

        tvVersionName.text = "App Version $versionName"
    }


}