package com.example.prototipo2tt.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Reservation(
    var id: Int,
    var boleta: String,
    var student: String,
    @SerializedName("num_pc") var computer: Int,
    var status: String,
    @SerializedName("schedule_date") var date: String,
    @SerializedName("schedule_time") var hour: String,
) : Serializable
