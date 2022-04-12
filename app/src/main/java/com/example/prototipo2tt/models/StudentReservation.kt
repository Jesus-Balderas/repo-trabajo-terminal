package com.example.prototipo2tt.models

import com.google.gson.annotations.SerializedName

data class StudentReservation(
    var id : Int,
    @SerializedName("schedule_date") var date : String,
    @SerializedName("schedule_time") var hour : String,
    var status: String,
    @SerializedName("created_at") var createdDate: String,
    var laboratory: Laboratory,
    var attendant: Attendant,
    var computer: Computer
)
