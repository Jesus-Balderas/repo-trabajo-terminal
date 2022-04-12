package com.example.prototipo2tt.models

import java.io.Serializable

data class Attendant (
    var id: Int,
    var num_empleado: String,
    var name: String,
    var first_name: String,
    var second_name: String,
    var email: String,
    var laboratories : ArrayList<Laboratory>

):Serializable {
    override fun toString(): String {
        return name
    }
}
