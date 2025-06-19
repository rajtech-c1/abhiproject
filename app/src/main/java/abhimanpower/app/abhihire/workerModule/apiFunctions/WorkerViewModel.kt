package abhimanpower.app.abhihire.workerModule.apiFunctions

import abhimanpower.app.abhihire.volunteerModule.modalClass.AddWorkerResponse
import abhimanpower.app.abhihire.volunteerModule.modalClass.WorkerData
import abhimanpower.app.abhihire.workerModule.modalClass.GetAvailableWorksResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject


@HiltViewModel
class WorkerViewModel @Inject constructor(
    private val workerRepository: WorkerRepository
) : ViewModel() {

    private var getAvailableWorksResponse = MutableLiveData<GetAvailableWorksResponse>()

    fun getAvailableWorks() {
        viewModelScope.launch {
            getAvailableWorksResponse.postValue(workerRepository.getAvailableWorks())
        }
    }

    fun resetGetAvailableWorksResponse() {
        getAvailableWorksResponse = MutableLiveData<GetAvailableWorksResponse>()
    }

    fun getAvailableWorksResponse(): LiveData<GetAvailableWorksResponse> {
        return getAvailableWorksResponse
    }

    private var updateWorkerResponse = MutableLiveData<AddWorkerResponse>()

    fun updateWorkerProfile(workerData: WorkerData, photo: MultipartBody.Part) {
        viewModelScope.launch {
            updateWorkerResponse.postValue(workerRepository.updateWorkerProfile(workerData, photo))
        }
    }

    fun resetUpdateWorkerResponse() {
        updateWorkerResponse = MutableLiveData<AddWorkerResponse>()
    }

    fun getUpdateWorkerResponse(): LiveData<AddWorkerResponse> {
        return updateWorkerResponse
    }

    private var deletePostResponse = MutableLiveData<GetAvailableWorksResponse>()

    fun deletePost(postId:Int) {
        viewModelScope.launch {
            deletePostResponse.postValue(workerRepository.deletePostResponse(postId))
        }
    }

    fun resetDeletePostResponse() {
        deletePostResponse = MutableLiveData<GetAvailableWorksResponse>()
    }

    fun getDeletePostResponse(): LiveData<GetAvailableWorksResponse> {
        return deletePostResponse
    }
}