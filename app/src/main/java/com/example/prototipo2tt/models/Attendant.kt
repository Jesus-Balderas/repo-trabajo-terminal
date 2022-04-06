package com.example.prototipo2tt.models

import java.io.Serializable

data class Attendant (
    var id: Int,
    var name: String
):Serializable {
    override fun toString(): String {
        return name
    }
}
