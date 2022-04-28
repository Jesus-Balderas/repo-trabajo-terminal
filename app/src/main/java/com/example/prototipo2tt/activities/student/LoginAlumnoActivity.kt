
package com.example.prototipo2tt.activities.student

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.prototipo2tt.PreferenceHelper
import com.example.prototipo2tt.R
import com.example.prototipo2tt.PreferenceHelper.get
import com.example.prototipo2tt.PreferenceHelper.set
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.io.response.LoginStudentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginAlumnoActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

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
        //Desplegando el boton hacia atras
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        bindUI()

        //Accedemos a las Preferencias
        val preferences = PreferenceHelper.customPrefs(this,"jwt-student")
        //Si la variable session de las Preferencias no es igual a false (valor por defecto)
        //significa que hay una sesión activa y lo redirige al HomeActivity
        if (preferences["jwt-student", ""].contains(".")){

            goToHomeAlumno()
        }

        btnLoginAlumno.setOnClickListener {

            postLoginStudent()

        }

        btnRegisterAlumno.setOnClickListener {
            val intentRegisterAlumno = Intent(this@LoginAlumnoActivity, RegistrarAlumnoActivity::class.java)
            startActivity(intentRegisterAlumno)
        }

    }

    private fun postLoginStudent()
    {
        val numBoleta = editTextBoleta.text.toString()
        val password = editTextPasswordAlumno.text.toString()

        if (numBoleta.trim().isEmpty() || password.trim().isEmpty()) {

            errorEmptyCredentials()
            return
        }

        if (numBoleta.length < 10 || password.length < 4 ) {

            errorLengthCredentials()
            return
        }

        val call = apiService.postLoginStudent(numBoleta, password)
        call.enqueue(object : Callback<LoginStudentResponse>{
            override fun onResponse(
                call: Call<LoginStudentResponse>,
                response: Response<LoginStudentResponse>
            ) {
                if (response.isSuccessful){
                    val loginStudentResponse = response.body()
                    if (loginStudentResponse == null) {
                        Toast.makeText(this@LoginAlumnoActivity,
                            "Se obtuvo una respuesta inesperada del servidor",
                            Toast.LENGTH_SHORT).show()
                        return
                    }

                    if (loginStudentResponse.success) {
                        createSessionPreference(loginStudentResponse.token)
                        goToHomeAlumno()

                    } else {
                        errorInvalidCredencials()
                    }

                } else {
                    Toast.makeText(this@LoginAlumnoActivity,
                        "Se obtuvo una respuesta inesperada del servidor",
                        Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<LoginStudentResponse>, t: Throwable) {
                Toast.makeText(this@LoginAlumnoActivity,
                    t.localizedMessage,
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun createSessionPreference(jwt: String) {

        //Accedemos a las Preferencias
        val preferences = PreferenceHelper.customPrefs(this,"jwt-student")
        //Modificamos la variable session de la Preferencias para guardar la sesion activa del usuario Alumno
        preferences["jwt-student"] = jwt
    }

    private fun bindUI() {

        btnLoginAlumno = findViewById(R.id.btnLoginAlumno)
        btnRegisterAlumno = findViewById(R.id.btnRegisterAlumno)
        btnForgetPasswordAlumno = findViewById(R.id.btnForgetPasswordAlumno)
        editTextBoleta = findViewById(R.id.editTextBoleta)
        editTextPasswordAlumno = findViewById(R.id.editTextPasswordAlumno)
    }

    private fun goToHomeAlumno() {

        val intentHomeAlumno = Intent(this@LoginAlumnoActivity, HomeAlumnoActivity::class.java )
        startActivity(intentHomeAlumno)
        finish()
    }

    private fun errorEmptyCredentials() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Credenciales vacías ")
        builder.setMessage("Por favor, ingrese su número de boleta y contraseña.")
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun errorLengthCredentials(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Boleta y Contraseña ")
        builder.setMessage("El número de boleta debe tener al menos 10 caracteres y la contraseña debe ser de 5 caracteres o más.")
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun errorInvalidCredencials(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Credenciales Invalidas")
        builder.setMessage("Los datos que ingresaste son incorrectos. Intenta de nuevo.")
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }


}