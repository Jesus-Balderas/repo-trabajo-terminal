package com.example.prototipo2tt.activities.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.prototipo2tt.R
import androidx.appcompat.widget.Toolbar
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.models.Career
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
        bindUI()

        loadCareers()


    }

    private fun loadCareers(){
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
            }

            override fun onFailure(call: Call<ArrayList<Career>>, t: Throwable) {
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