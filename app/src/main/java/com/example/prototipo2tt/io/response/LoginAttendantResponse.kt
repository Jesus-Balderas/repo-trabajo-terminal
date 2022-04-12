package com.example.prototipo2tt.io.response

import com.example.prototipo2tt.models.Attendant

data class LoginAttendantResponse(
    var success : Boolean,
    var attendant : Attendant,
    var token: String
)
