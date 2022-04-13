package com.example.prototipo2tt.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AttendantReservation(
    var id : Int,
    @SerializedName("schedule_date") var date : String,
    @SerializedName("schedule_time") var time : String,
    var status : String,
    @SerializedName("created_at") var createdDate : String,
    var student : Student,
    var computer : Computer

): Serializable
