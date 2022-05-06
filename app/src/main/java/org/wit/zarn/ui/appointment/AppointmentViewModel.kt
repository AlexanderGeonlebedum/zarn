package org.wit.zarn.ui.appointment


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.zarn.models.ZarnManager
import org.wit.zarn.models.ZarnModel
import timber.log.Timber
import java.lang.Exception

class AppointmentViewModel : ViewModel() {

    private val zarnsList = MutableLiveData<List<ZarnModel>>()

    val observableZarnsList: LiveData<List<ZarnModel>>
        get() = zarnsList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {
        load()
    }

    fun load() {
        try {
            ZarnManager.findAll(liveFirebaseUser.value?.email!!, zarnsList)
            Timber.i("Retrofit Load Success : $zarnsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Load Error : $e.message")
        }
    }

    fun delete(email: String, id: String) {
        try {
            ZarnManager.delete(email,id)
            Timber.i("Retrofit Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Delete Error : $e.message")
        }
    }
}

