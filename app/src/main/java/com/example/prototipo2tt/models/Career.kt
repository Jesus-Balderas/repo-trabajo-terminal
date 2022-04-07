package com.example.prototipo2tt.models

data class Career(
    var id : Int,
    var name : String
) {
    override fun toString(): String {
        return name
    }
}
