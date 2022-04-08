package org.wit.zarn.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ZarnModel (var id: Long = 0,
                      val clientAddName: String,
                      val clientAddNumber: Int = 0,
                      val chosenAppointments: String = "N/A") : Parcelable



//val datePicker:String