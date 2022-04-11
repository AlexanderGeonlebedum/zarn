package org.wit.zarn.ui.appointment


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.zarn.models.ZarnManager
import org.wit.zarn.models.ZarnModel

class AppointmentViewModel : ViewModel() {

    private val zarnsList = MutableLiveData<List<ZarnModel>>()

    val observableZarnsList: LiveData<List<ZarnModel>>
        get() = zarnsList

    init {
        load()
    }

    fun load() {
        zarnsList.value = ZarnManager.findAll()
    }
}