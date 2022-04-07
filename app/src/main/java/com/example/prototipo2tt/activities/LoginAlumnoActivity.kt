package com.example.prototipo2tt.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.example.prototipo2tt.PreferenceHelper
import com.example.prototipo2tt.R
import com.example.prototipo2tt.PreferenceHelper.get
import com.example.prototipo2tt.PreferenceHelper.set

class LoginAlumnoActivity : AppCompatActivity() {

    private lateinit var btnRegisterAlumno : Button
    private lateinit var btnLoginAlumno : Button
    private lateinit var btnForgetPasswordAlumno : Button
    private lateinit var editTextBoleta : EditText
    private lateinit var editTextPasswordAlumno: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_alumno)

        //Agregando el Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarmain)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        bindUI()

        //Accedemos a las Preferencias
        val preferences = PreferenceHelper.customPrefs(this,"session-student")
        //Si la variable session de las Preferencias no es igual a false (valor por defecto)
        //significa que hay una sesión activa y lo redirige al HomeActivity
        if (preferences["session-student", false]){
            goToHomeAlumno()
        }

        btnLoginAlumno.setOnClickListener {
            createSessionPreference()
            goToHomeAlumno()

        }

        btnRegisterAlumno.setOnClickListener {
            val intentRegisterAlumno = Intent(this@LoginAlumnoActivity, RegistrarAlumnoActivity::class.java)
            startActivity(intentRegisterAlumno)
        }

    }

    private fun bindUI()
    {
        btnLoginAlumno = findViewById(R.id.btnLoginAlumno)
        btnRegisterAlumno = findViewById(R.id.btnRegisterAlumno)
        btnForgetPasswordAlumno = findViewById(R.id.btnForgetPasswordAlumno)
        editTextBoleta = findViewById(R.id.editTextBoleta)
        editTextPasswordAlumno = findViewById(R.id.editTextPasswordAlumno)
    }

    private fun goToHomeAlumno()
    {
        val intentHomeAlumno = Intent(this@LoginAlumnoActivity,HomeAlumnoActivity::class.java )
        startActivity(intentHomeAlumno)
        finish()
    }

    private fun createSessionPreference()
    {
        //Accedemos a las Preferencias
        val preferences = PreferenceHelper.customPrefs(this,"session-student")
        //Modificamos la variable session de la Preferencias para guardar la sesion activa del usuario Alumno
        preferences["session-student"] = true
    }
}