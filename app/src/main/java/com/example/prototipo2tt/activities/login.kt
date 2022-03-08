package com.example.prototipo2tt.activities

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import com.example.prototipo2tt.R
import android.content.Intent
import android.view.View
import android.widget.Button
import com.example.prototipo2tt.activities.registrar_alumno
import com.example.prototipo2tt.activities.alumno_home
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class login : AppCompatActivity() {
    var btnRegister: Button? = null
    var btnLogin: Button? = null
    var btnForgetPassword: Button? = null
    var editTextBoleta: EditText? = null
    var editTextPassword: EditText? = null
    var toolb: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_alumno)
        bindUI()
        toolb = findViewById<View>(R.id.toolbarmain) as Toolbar
        setSupportActionBar(toolb)

        //BOTÓN REGISTRAR
        btnRegister!!.setOnClickListener {
            val intent2 = Intent(this@login, registrar_alumno::class.java)
            startActivity(intent2)
        }


    }

    private fun bindUI() {
        btnRegister = findViewById<View>(R.id.btnRegisterAlumno) as Button
        btnLogin = findViewById<View>(R.id.btnLoginAlumno) as Button
        btnForgetPassword = findViewById<View>(R.id.btnForgetPassword) as Button
        editTextBoleta = findViewById<View>(R.id.editTextBoleta) as EditText
        editTextPassword = findViewById<View>(R.id.editTextPassword) as EditText
    }

    private fun isValidBoleta(boleta: String): Boolean {
        return if (boleta.length > 0 && boleta.length <= 10) {
            true
        } else {
            false
        }
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 4
    }

    private fun goToAlumnoHome() {
        val intent1 = Intent(this@login, alumno_home::class.java)
        intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent1)
    }


}