package org.wit.zarn.ui.appointment


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.zarn.models.ZarnManager
import org.wit.zarn.models.ZarnModel
import timber.log.Timber
import java.lang.Exception

class AppointmentViewModel : ViewModel() {

    private val zarnsList = MutableLiveData<List<ZarnModel>>()

    val observableZarnsList: LiveData<List<ZarnModel>>
        get() = zarnsList

    init {
        load()
    }

    fun load() {
        try {
            ZarnManager.findAll(zarnsList)
            Timber.i("Retrofit Load Success : $zarnsList.value")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Load Error : $e.message")
        }
    }

    fun delete(id: String) {
        try {
            ZarnManager.delete(id)
            Timber.i("Retrofit Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Delete Error : $e.message")
        }
    }
}

