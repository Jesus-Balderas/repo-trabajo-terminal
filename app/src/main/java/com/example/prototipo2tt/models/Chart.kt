package com.example.prototipo2tt.models

data class Chart(
    var laboratory: ArrayList<Laboratory>,
    var reject : Int,
    var cancel : Int,
    var finish : Int
)
