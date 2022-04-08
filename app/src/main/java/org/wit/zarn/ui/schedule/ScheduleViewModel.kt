package org.wit.zarn.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.zarn.models.ZarnModel
import org.wit.zarn.models.ZarnManager

class ScheduleViewModel : ViewModel() {
    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addZarn(zarn: ZarnModel) {
        status.value = try {
            ZarnManager.create(zarn)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}