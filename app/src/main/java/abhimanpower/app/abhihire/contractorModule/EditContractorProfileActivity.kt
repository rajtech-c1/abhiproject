package abhimanpower.app.abhihire.contractorModule

import abhimanpower.app.abhihire.R
import abhimanpower.app.abhihire.contractorModule.apiFunctions.ContractorApiFunction
import abhimanpower.app.abhihire.contractorModule.apiFunctions.ContractorViewModel
import abhimanpower.app.abhihire.contractorModule.uiFunctions.EditContractorProfileUI
import abhimanpower.app.abhihire.databinding.ActivityEditContractorProfileBinding
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials
import abhimanpower.app.abhihire.loginModule.modalClass.LoginCredentials.workerAccountData
import abhimanpower.app.abhihire.volunteerModule.modalClass.AddContractorResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.ContractorData
import abhimanpower.app.abhihire.zCommonFunctions.AppData
import abhimanpower.app.abhihire.zCommonFunctions.UtilFunctions
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class EditContractorProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditContractorProfileBinding

    private lateinit var editContractorProfileUI: EditContractorProfileUI
    private lateinit var contractorViewModel: ContractorViewModel
    private lateinit var contractorApiFunction: ContractorApiFunction


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditContractorProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contractorViewModel = ViewModelProvider(this)[ContractorViewModel::class.java]

        initViews()
        setData()
        setImage()
        setOnClickListeners()
        setupClickListeners()
    }


    private fun setImage() {
        if (AppData.getUserRole(this) == 3) {
            Log.e(
                "Test",
                "Contractor Image Loading : ${LoginCredentials.contractorAccountData.image}"
            )
            if (LoginCredentials.contractorAccountData.image.isNotEmpty()) {
                binding.profilePic.load(LoginCredentials.contractorAccountData.image) {
                    placeholder(R.drawable.ic_add_photo) // Make sure you have this drawable
                    crossfade(true)
                    crossfade(1000)
                }
            } else {
                binding.profilePic.setImageResource(R.drawable.ic_shop_owner)
            }
        } else {
            Log.e("Test", "General User Image Loading : ")

        }
    }

    private fun setData() {
        if (AppData.getUserRole(this) == 3) {
            val contractorAccountData = LoginCredentials.contractorAccountData
            Log.e("Test", "Contractor Account Details : $workerAccountData")

            binding.etMobileNo.setText(contractorAccountData.mobileNo)
            binding.etUserName.setText(contractorAccountData.name)
            binding.etEmail.setText(contractorAccountData.email)

//            binding.etSelectDate.text = workerAccountData.dob

//            if (LoginCredentials.workerAccountData.gender.toInt() == 1) {
//                binding.rbFemale.isChecked = true
//            } else {
//                binding.rbMale.isChecked = true
//            }

            binding.etStreet.setText(contractorAccountData.street)
            binding.etArea.setText(contractorAccountData.area)
            binding.etPincode.setText(contractorAccountData.pincode)
//            binding.etState.setText(AreaData.getState(workerAccountData.state.toInt()))


        }

    }

    private fun initViews() {
        editContractorProfileUI = EditContractorProfileUI(this, binding, ::updateWorkerProfile)
        contractorApiFunction =
            ContractorApiFunction(
                this,
                this,
                contractorViewModel,
                onUpdateContractorProfile = ::updateContractorProfileResponse
            )
    }

    private fun setOnClickListeners() {
        binding.btBack.setOnClickListener {
            finish()
        }
    }

    private fun updateWorkerProfile(contractorData: ContractorData) {

        if (imageSelectedFlag) {
            contractorData.imageSelected = 1
            contractorData.volunteerId = LoginCredentials.contractorAccountData.volunteerId.toInt()
            contractorData.contractorId = LoginCredentials.contractorAccountData.contractorId

            val imageMP = getImageMultipart()

            if (imageMP != null) {
                contractorApiFunction.updateContractorProfile(contractorData, imageMP)
            } else {
                UtilFunctions.showToast(this, "Image is Null")
            }
        } else {
            contractorData.imageSelected = 0
            contractorData.profileImageUrl = LoginCredentials.contractorAccountData.image
            contractorData.volunteerId = LoginCredentials.contractorAccountData.volunteerId.toInt()
            contractorData.contractorId = LoginCredentials.contractorAccountData.contractorId

            contractorApiFunction.updateContractorProfile(contractorData, createDummyImagePart())
        }

    }

    private fun updateContractorProfileResponse(response: AddContractorResponse) {

        when (response.status) {
            200 -> {
                LoginCredentials.contractorAccountData = response.accountData
                UtilFunctions.showToast(this, "Profile Update Successfully")
                finish()
            }

            else -> {
                UtilFunctions.showToast(this, response.message)
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

    private fun setupClickListeners() {
        binding.btBack.setOnClickListener {
            finish()
        }

        binding.profilePic.setOnClickListener {
            requestCameraAndStoragePermissions()
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