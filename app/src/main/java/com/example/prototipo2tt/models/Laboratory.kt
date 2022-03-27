package com.example.prototipo2tt.models

import java.io.Serializable

data class Laboratory (
    var id: Int,
    var name: String,
    var classroom: String,
    var edifice: String,
    var status: String
): Serializable