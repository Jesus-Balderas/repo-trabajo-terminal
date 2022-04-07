package com.example.prototipo2tt.io.response

import com.example.prototipo2tt.models.Student

data class LoginStudentResponse(
    var success: Boolean,
    var student: Student,
    var token: String
)
