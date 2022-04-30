package com.example.prototipo2tt.activities.attendant

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import com.example.prototipo2tt.R
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.prototipo2tt.PreferenceHelper
import com.example.prototipo2tt.PreferenceHelper.get
import com.example.prototipo2tt.PreferenceHelper.set
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.io.response.LoginAttendantResponse
import com.example.prototipo2tt.models.LoadingDialogBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginEncargadoActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private lateinit var editTextNumEmpleado: EditText
    private lateinit var editTextPasswordEncargado: EditText
    private lateinit var btnLoginEncargado: Button
    private lateinit var btnForgetPasswordEncargado: Button
    private lateinit var toolbar: Toolbar

    private lateinit var progressBar:LoadingDialogBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_encargado)

        toolbar = findViewById(R.id.toolbarLoginProfesor)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        //Desplegando el boton hacia atras
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = LoadingDialogBar(this)

        bindUi()

        //Accedemos a las Preferencias
        val preferences = PreferenceHelper.customPrefs(this, "jwt-attendant")
        //Si la variable session de las Preferencias no es igual a false (valor por defecto)
        //significa que hay una sesión activa y lo redirige al HomeActivity
        if (preferences["jwt-attendant", ""].contains(".")){
            goToHomeEncargado()
        }

        btnLoginEncargado.setOnClickListener {
            postLoginAttendant()
        }

    }

    private fun postLoginAttendant(){

        val numEmpleado = editTextNumEmpleado.text.toString()
        val password = editTextPasswordEncargado.text.toString()

        if (numEmpleado.trim().isEmpty() || password.trim().isEmpty()) {

            errorEmptyCredentials()
            return
        }
        progressBar.ShowDialog("Iniciando sesión...")
        val call = apiService.postLoginAttendant(numEmpleado,password)
        call.enqueue(object:Callback<LoginAttendantResponse>{
            override fun onResponse(
                call: Call<LoginAttendantResponse>,
                response: Response<LoginAttendantResponse>
            ) {
                if (response.isSuccessful){
                    val loginAttendantResponse = response.body()
                    if (loginAttendantResponse == null) {
                        progressBar.HideDialog()
                        Toast.makeText(this@LoginEncargadoActivity,
                            "Se obtuvo una respuesta inesperada del servidor",
                            Toast.LENGTH_SHORT).show()
                        return
                    }

                    if (loginAttendantResponse.success) {
                        progressBar.HideDialog()
                        createSessionPreference(loginAttendantResponse.token)
                        goToHomeEncargado()

                    } else {
                        progressBar.HideDialog()
                        errorInvalidCredencials()
                    }

                } else {
                    progressBar.HideDialog()
                    Toast.makeText(this@LoginEncargadoActivity,
                        "Se obtuvo una respuesta inesperada del servidor",
                        Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<LoginAttendantResponse>, t: Throwable) {
                progressBar.HideDialog()
                Toast.makeText(this@LoginEncargadoActivity,
                    t.localizedMessage,
                    Toast.LENGTH_SHORT).show()
            }

        })
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

    private fun errorEmptyCredentials(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Credenciales vacías ")
        builder.setMessage("Por favor, ingrese su número de empleado y contraseña.")
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun goToHomeEncargado() {
        val intent1 = Intent(this@LoginEncargadoActivity, HomeEncargadoActivity::class.java)
        startActivity(intent1)
        finish()
    }

    private fun createSessionPreference(jwt : String)
    {
        //Accedemos a las Preferencias
        val preferences = PreferenceHelper.customPrefs(this, "jwt-attendant")
        //Modificamos la variable session de la Preferencias para guardar la sesion activa del usuario Alumno
        preferences["jwt-attendant"] = jwt
    }

    private fun bindUi() {
        btnLoginEncargado = findViewById(R.id.btnLoginEncargado)
        btnForgetPasswordEncargado = findViewById(R.id.btnForgetPasswordEncargado)
        editTextNumEmpleado = findViewById(R.id.editTextNumEmpleado)
        editTextPasswordEncargado = findViewById(R.id.editTextPasswordEncargado)
    }
}