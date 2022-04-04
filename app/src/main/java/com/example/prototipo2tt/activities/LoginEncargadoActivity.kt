package com.example.prototipo2tt.activities

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import com.example.prototipo2tt.R
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.prototipo2tt.PreferenceHelper
import com.example.prototipo2tt.PreferenceHelper.get
import com.example.prototipo2tt.PreferenceHelper.set
import com.example.prototipo2tt.activities.HomeEncargadoActivity
import com.example.prototipo2tt.activities.RegistrarEncargadoActivity

class LoginEncargadoActivity : AppCompatActivity() {

    private lateinit var editTextNumEmpleado: EditText
    private lateinit var editTextPasswordEncargado: EditText
    private lateinit var btnLoginEncargado: Button
    private lateinit var btnRegisterEncargado: Button
    private lateinit var btnForgetPasswordEncargado: Button
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_encargado)

        toolbar = findViewById(R.id.toolbarLoginProfesor)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

        bindUi()

        //Accedemos a las Preferencias
        val preferences = PreferenceHelper.defaultPrefs(this)
        //Si la variable session de las Preferencias no es igual a false (valor por defecto)
        //significa que hay una sesión activa y lo redirige al HomeActivity
        if (preferences["session", false]){
            goToHomeEncargado()
        }

        btnLoginEncargado.setOnClickListener {
            createSessionPreference()
            goToHomeEncargado()
        }

        btnRegisterEncargado.setOnClickListener {
            val intent2 = Intent(this@LoginEncargadoActivity, RegistrarEncargadoActivity::class.java)
            startActivity(intent2)
        }
    }

    private fun goToHomeEncargado() {
        val intent1 = Intent(this@LoginEncargadoActivity, HomeEncargadoActivity::class.java)
        startActivity(intent1)
        finish()
    }

    private fun createSessionPreference()
    {
        //Accedemos a las Preferencias
        val preferences = PreferenceHelper.defaultPrefs(this)
        //Modificamos la variable session de la Preferencias para guardar la sesion activa del usuario Alumno
        preferences["session"] = true
    }

    private fun bindUi() {
        btnLoginEncargado = findViewById(R.id.btnLoginEncargado)
        btnRegisterEncargado = findViewById(R.id.btnRegisterEncargado)
        btnForgetPasswordEncargado = findViewById(R.id.btnForgetPasswordEncargado)
        editTextNumEmpleado = findViewById(R.id.editTextNumEmpleado)
        editTextPasswordEncargado = findViewById(R.id.editTextPasswordEncargado)
    }
}