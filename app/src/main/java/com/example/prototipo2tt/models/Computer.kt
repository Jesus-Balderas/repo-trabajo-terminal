package com.example.prototipo2tt.models

import java.io.Serializable

data class Computer (
    var id: Int,
    var num_pc: Int
):Serializable {
    override fun toString(): String {
        return num_pc.toString()
    }
}