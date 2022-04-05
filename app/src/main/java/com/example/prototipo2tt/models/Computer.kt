package com.example.prototipo2tt.models

data class Computer (
    var id: Int,
    var num_pc: Int
) {
    override fun toString(): String {
        return num_pc.toString()
    }
}