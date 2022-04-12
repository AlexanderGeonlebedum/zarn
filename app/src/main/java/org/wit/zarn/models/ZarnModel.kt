package org.wit.zarn.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ZarnModel (var _id: String = "N/A",
                      @SerializedName("bookingtype")
                      val clientAddName: String,
                      val clientAddNumber: Int = 0,
                      val message: String = "n/a",
                      val chosenAppointments: String = "N/A") : Parcelable



//val datePicker:String