package com.example.prototipo2tt.models

import java.io.Serializable

data class Reservation(
    var id: Int,
    var student: String,
    var laboratory: String,
    var computer: Int,
    var hourStart: String,
    var hourEnd: String,
    var date: String,
    var status: String
) : Serializable
