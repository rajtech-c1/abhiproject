package abhimanpower.app.abhihire.loginModule

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.databinding.ActivityRoleSelectionBinding
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.zCommonFunctions.CallIntent
import abhimanpower.app.abhihire.zCommonFunctions.StatusBarUtils
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class RoleSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoleSelectionBinding
    private var selectedRole: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoleSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        StatusBarUtils.transparentStatusBar(this)
//        StatusBarUtils.setTopPadding(resources, binding.mainLt)

//        UtilFunctions.showLanguageSelectionDialog(this, this)
//        if (!UtilFunctions.isLanguageSelected) {
//            UtilFunctions.showLanguageSelectionDialog(this, this)
//            UtilFunctions.isLanguageSelected = true
//        }

        setupRoleSelection()
        setupClickListener()
    }


    private fun setupRoleSelection() {
        // Create a list of all role card views and their corresponding radio buttons
        val roleViews = listOf(
            RoleView(
                binding.rollVolunteer,
                binding.rollVolunteerLt,
                binding.root.findViewById<RadioButton>(
                    R.id.radioButtonVolunteer
                ),
                "Volunteer"
            ),
            RoleView(
                binding.rollWorker,
                binding.rollWorkerLt,
                binding.root.findViewById<RadioButton>(R.id.radioButtonWorker),
                "Worker"
            ),
            RoleView(
                binding.rollContractor,
                binding.rollContractorLt,
                binding.root.findViewById<RadioButton>(R.id.radioButtonContractor),
                "Contractor"
            ),
            RoleView(
                binding.rollSingleUser,
                binding.rollSingleUserLt,
                binding.root.findViewById<RadioButton>(R.id.radioButtonSingleUser),
                "SingleUser"
            )
        )

        // Set click listeners for each role
        roleViews.forEach { roleView ->
            roleView.cardView.setOnClickListener {
                selectRole(roleViews, roleView)
            }
            roleView.radioButton.setOnClickListener {
                selectRole(roleViews, roleView)
            }
        }
    }

    private fun selectRole(allRoles: List<RoleView>, selectedRoleView: RoleView) {
        allRoles.forEach { roleView ->
            val isSelected = roleView == selectedRoleView
            roleView.radioButton.isChecked = isSelected

            // Update card appearance based on selection
            if (isSelected) {
                roleView.constraintLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.selected_role_color
                    )
                )
                selectedRole = roleView.roleName
            } else {
                roleView.constraintLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.transparent
                    )
                )
            }
        }
    }

    private fun setupClickListener() {
        binding.btSendOTP.setOnClickListener {
            if (selectedRole.isEmpty()) {
                Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show()
            } else {
                when (selectedRole) {
                    "Volunteer" -> LoginCredentials.selectedRole = 2
                    "Worker" -> LoginCredentials.selectedRole = 1
                    "Contractor" -> LoginCredentials.selectedRole = 3
                    else -> LoginCredentials.selectedRole = 4
                }
                // Proceed with the selected role
                CallIntent.gotoNextActivity(this, false, this, VolunteerLoginActivity())

            }
        }

        binding.btTranslate.setOnClickListener {
            UtilFunctions.showLanguageSelectionDialog(this, this)

        }
    }

    // Data class to hold role view components
    data class RoleView(
        val cardView: CardView,
        val constraintLayout: ConstraintLayout,
        val radioButton: RadioButton,
        val roleName: String
    )
}