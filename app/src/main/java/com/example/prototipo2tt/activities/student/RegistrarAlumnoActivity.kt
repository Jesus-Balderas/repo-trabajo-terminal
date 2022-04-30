package com.example.prototipo2tt.activities.student

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import com.example.prototipo2tt.R
import androidx.appcompat.widget.Toolbar
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.io.response.StudentReservationResponse
import com.example.prototipo2tt.models.Career
import com.example.prototipo2tt.models.LoadingDialogBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrarAlumnoActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }


    private lateinit var btnRegister: Button
    private lateinit var editTextNumBoleta: EditText
    private lateinit var editTextNombres: EditText
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextSecondName: EditText
    private lateinit var editTextEmailAlumno: EditText
    private lateinit var editTextPasswordAlumno: EditText
    private lateinit var editTextConfPasswordAlumno: EditText
    private lateinit var spinnerCarreras: Spinner

    private lateinit var progressBar:LoadingDialogBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_alumno)

        //Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarRegistrarAlumno)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        //Desplegando el boton hacia atras
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = LoadingDialogBar(this)

        bindUI()

        loadCareers()

        btnRegister.setOnClickListener {
            performRegister()
        }



    }

    private fun performRegister(){

        val boleta = editTextNumBoleta.text.toString()
        val name = editTextNombres.text.toString()
        val firstName = editTextFirstName.text.toString()
        val secondName = editTextSecondName.text.toString()
        val email = editTextEmailAlumno.text.toString()
        val password = editTextPasswordAlumno.text.toString()
        val cpassword = editTextConfPasswordAlumno.text.toString()
        val career = spinnerCarreras.selectedItem as Career
        val careerId = career.id

        if (boleta.isEmpty() || boleta.length < 10){
            editTextNumBoleta.error = "Ingresa tu número de boleta y que no sea menor a 10 caracteres."
            return
        }
        if (name.isEmpty()) {
            editTextNombres.error = "Ingresa tu(s) nombres(s)."
            return
        }
        if (firstName.isEmpty()){
            editTextFirstName.error = "Ingresa tu primer apellido."
            return
        }

        if (secondName.isEmpty()){
            editTextSecondName.error = "Ingresa tu segundo apellido."
            return
        }

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            editTextEmailAlumno.setTextColor(Color.parseColor("#16CC1E"))

        } else {
            editTextEmailAlumno.setTextColor(Color.parseColor("#0070c9"))
            editTextEmailAlumno.error = "Ingresa un correo electronico valido."
            return
        }

        if (password.length < 5) {
            editTextPasswordAlumno.error = "Ingresa una contraseña y que sea mayor a 5 caracteres."
            return
        }

        if (cpassword != password){
            editTextConfPasswordAlumno.error = "Las contraseñas no coinciden."
            return
        }

        postRegisterStudent(boleta, name, firstName, secondName, email, careerId, password)

    }

    private fun postRegisterStudent(
        boleta : String, name: String, firstName: String, secondName: String, email:String, careerId: Int, password: String ){

        progressBar.ShowDialog("Registrando...")

        val call = apiService.postRegisterStudent(boleta, name, firstName, secondName, email, careerId, password)
        call.enqueue(object: Callback<StudentReservationResponse>{
            override fun onResponse(
                call: Call<StudentReservationResponse>,
                response: Response<StudentReservationResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body()?.success == true){
                        progressBar.HideDialog()
                        successRegister()
                    } else {
                        progressBar.HideDialog()
                        failRegister()
                    }
                }
            }

            override fun onFailure(call: Call<StudentReservationResponse>, t: Throwable) {
                progressBar.HideDialog()
                Toast.makeText(this@RegistrarAlumnoActivity,
                    "Ocurrio un problema al realizar el registro.", Toast.LENGTH_SHORT).show()
            }

        })



    }

    private fun failRegister(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¡Falló tu registro!")
        builder.setMessage("El número de boleta que ingresaste ya fue registrado.")
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun successRegister(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¡Enhorabuena!")
        builder.setMessage("Te has registrado correctamente ahora puedes iniciar sesión.")
        builder.setPositiveButton("Iniciar sesión") { _, _ ->
            val intent = Intent(this, LoginAlumnoActivity::class.java)
            startActivity(intent)
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun loadCareers(){
        progressBar.ShowDialog("Cargando...")

        val call = apiService.getCareers()
        call.enqueue(object: Callback<ArrayList<Career>>{
            override fun onResponse(
                call: Call<ArrayList<Career>>,
                response: Response<ArrayList<Career>>
            ) {
                if (response.isSuccessful){
                    val careers = response.body()
                    spinnerCarreras.adapter = ArrayAdapter<Career>(
                        this@RegistrarAlumnoActivity, R.layout.spinner_item_design,
                        careers as MutableList<Career>
                    )
                }
                progressBar.HideDialog()
            }

            override fun onFailure(call: Call<ArrayList<Career>>, t: Throwable) {
                progressBar.HideDialog()
                Toast.makeText(this@RegistrarAlumnoActivity,
                    "No se pudieron cargar las carreras", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun bindUI() {
        //Button
        btnRegister = findViewById(R.id.btnRegistrar)
        //Edit Text
        editTextNumBoleta = findViewById(R.id.editTextNumBoleta)
        editTextNombres = findViewById(R.id.editTextNombres)
        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextSecondName = findViewById(R.id.editTextSecondName)
        editTextEmailAlumno = findViewById(R.id.editTextEmailAlumno)
        editTextPasswordAlumno = findViewById(R.id.editTextPass)
        editTextConfPasswordAlumno = findViewById(R.id.editTextConfPassword)
        spinnerCarreras = findViewById(R.id.spinnerCarreras)
    }
}