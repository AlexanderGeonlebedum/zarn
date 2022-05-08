package org.wit.zarn.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.zarn.firebase.FirebaseDBManager
import org.wit.zarn.models.ZarnModel
import timber.log.Timber
import java.lang.Exception

class ScheduleDetailViewModel : ViewModel() {
    private val zarn = MutableLiveData<ZarnModel>()

    var observableZarn: LiveData<ZarnModel>
         get() = zarn
         set(value) {zarn.value = value.value}

    fun getZarn(userid:String, id: String) {
        try {
            FirebaseDBManager.findById(userid, id, zarn)
            Timber.i("Detail getZarn() Success : ${
                zarn.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getZarn() Error : $e.message")
        }
    }

    fun updateZarn(userid:String, id: String,zarn: ZarnModel) {
        try {
            FirebaseDBManager.update(userid, id, zarn)
            Timber.i("Detail update() Success : $zarn")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}

