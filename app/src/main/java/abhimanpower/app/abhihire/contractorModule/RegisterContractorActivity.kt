package abhimanpower.app.abhihire.contractorModule

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.contractorModule.uiFunctions.RegisterContractorUI
import abhimanpower.app.abhihire.databinding.ActivityAddContractorBinding
import abhimanpower.app.abhihire.databinding.ActivityRegisterContractorBinding
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.volunteerModule.apiFunctions.VolunteerApiFunctions
import abhimanpower.app.abhihire.volunteerModule.apiFunctions.VolunteerViewModule
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddContractorResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.ContractorData
import abhimanpower.app.abhihire.volunteerModule.uiFuntion.AddContractorUI
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class RegisterContractorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterContractorBinding
    private lateinit var registerContractorUI: RegisterContractorUI

    private lateinit var volunteerViewModule: VolunteerViewModule
    private lateinit var volunteerApiFunctions: VolunteerApiFunctions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterContractorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        volunteerViewModule = ViewModelProvider(this)[VolunteerViewModule::class.java]


        initView()
        setOnClickListeners()

    }


    private fun setOnClickListeners()
    {
        binding.btBack.setOnClickListener {
            finish()
        }

        binding.profilePic.setOnClickListener {
            requestCameraAndStoragePermissions()
        }
    }

    private fun initView() {
        registerContractorUI = RegisterContractorUI(this, binding, ::postAddContractor)
        volunteerApiFunctions =
            VolunteerApiFunctions(
                this,
                this,
                volunteerViewModule,
                onAddWorkerResponse = {},
                onGetOverAllStatsResponse = {},
                onAddContractorResponse = ::addContractorResponse)
    }




    private fun postAddContractor(contractorData: ContractorData) {

        if (imageSelectedFlag) {
            contractorData.imageSelected = 1
            contractorData.volunteerId = LoginCredentials.volunteerAccountData.volunteerId.toInt()

            val imageMP = getImageMultipart()

            if (imageMP != null) {
                volunteerApiFunctions.postAddContractor(contractorData, imageMP)
            } else {
                UtilFunctions.showToast(this, "Image is Null")
            }
        } else {
            contractorData.imageSelected = 0
            contractorData.volunteerId = LoginCredentials.volunteerAccountData.volunteerId.toInt()

            volunteerApiFunctions.postAddContractor(contractorData, createDummyImagePart())

        }

    }

    private fun addContractorResponse(addContractorResponse: AddContractorResponse) {

        when (addContractorResponse.status) {
            200 -> {
                UtilFunctions.showToast(this, "Contractor Added Successfully")
                finish()
            }

            else -> {
                UtilFunctions.showToast(this, addContractorResponse.message)
            }
        }

    }


    ///Image  Code

    private lateinit var cameraImageUri: Uri
    private var imagePath: String = ""
    private var imageSelectedFlag = false

    private var selectedImageUri: Uri? = null

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.profilePic.setImageURI(it)
                imagePath = getPathFromUri(it) ?: ""
                selectedImageUri = it

                imageSelectedFlag = true

            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                binding.profilePic.setImageURI(cameraImageUri)
                imagePath = cameraImageUri.path ?: ""
                selectedImageUri = cameraImageUri

                imageSelectedFlag = true
            }
        }



    private fun requestCameraAndStoragePermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES
            )
        } else {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                100
            )
        } else {
            showImagePickerDialog()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100 && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            showImagePickerDialog()
        } else {
            Toast.makeText(this, "Permissions required", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Choose from Gallery", "Take Photo")

        AlertDialog.Builder(this)
            .setTitle("Select Image")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openGallery()
                    1 -> openCamera()
                }
            }
            .show()
    }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun openCamera() {
        val imageFile = File.createTempFile("IMG_", ".jpg", cacheDir)
        cameraImageUri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            imageFile
        )
        cameraLauncher.launch(cameraImageUri)
    }

    private fun getPathFromUri(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        return cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(projection[0])
                it.getString(columnIndex)
            } else null
        }
    }

    // Optional: Convert to Multipart
    private fun getImageMultipart(): MultipartBody.Part? {
        return try {
            val uri = selectedImageUri ?: return null
            val inputStream = contentResolver.openInputStream(uri)
            val byteArray = inputStream?.readBytes() ?: return null
            inputStream.close()

            val reqFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), byteArray)
            val fileName = getFileNameFromUri(uri)

            MultipartBody.Part.createFormData("WorkerImage", fileName, reqFile)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getFileNameFromUri(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    result = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.lastPathSegment ?: "image.jpg"
        }
        return result as String
    }

    private fun createDummyImagePart(): MultipartBody.Part {
        val dummyBytes = ByteArray(0) // empty byte array
        val dummyRequestBody = dummyBytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("WorkerImage", "dummy.jpg", dummyRequestBody)
    }

}