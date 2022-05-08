package org.wit.zarn.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class ZarnModel (var uid: String? = "",
                      @SerializedName("bookingtype")
                      val clientAddName: String = "Unknown",
                      val clientAddNumber: Int = 0,
                      val message: String = "n/a",
                      val chosenAppointments: String = "N/A",
                      val email: String = "zarn@gmail.com") : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "clientAddName" to clientAddName,
            "clientAddNumber" to clientAddNumber,
            "message" to message,
            "chosenAppointments" to chosenAppointments,
            "email" to email
        )
    }
}

//val datePicker:String