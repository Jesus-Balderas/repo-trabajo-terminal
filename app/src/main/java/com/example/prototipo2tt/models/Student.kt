package com.example.prototipo2tt.models

import java.io.Serializable

data class Student(
    var id: Int,
    var num_boleta: String,
    var name: String,
    var first_name: String,
    var second_name: String,
    var email: String,
    var career: Career
):Serializable