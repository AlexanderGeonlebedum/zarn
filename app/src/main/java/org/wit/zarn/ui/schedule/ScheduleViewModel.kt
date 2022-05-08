package org.wit.zarn.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.zarn.firebase.FirebaseDBManager
import org.wit.zarn.models.ZarnModel
import timber.log.Timber

class ScheduleViewModel : ViewModel() {
    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addZarn(firebaseUser: MutableLiveData<FirebaseUser>,
                zarn: ZarnModel) {
        status.value = try {
//            ZarnManager.create(zarn)
            Timber.i("Zarn in ScheduleView")
            Timber.i(zarn.toString())
            FirebaseDBManager.create(firebaseUser,zarn)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}