package com.example.prototipo2tt.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.prototipo2tt.R
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.widget.Toolbar

class registrar_alumno : AppCompatActivity() {
    var buttonRegister: Button? = null
    var editTextNumBoleta: EditText? = null
    var editTextNombres: EditText? = null
    var editTextFirstName: EditText? = null
    var editTextSecondName: EditText? = null
    var editTextEmailAlumno: EditText? = null
    var editTextPasswordAlumno: EditText? = null
    var editTextConfPasswordAlumno: EditText? = null
    var toolb: Toolbar? = null
    var se: Spinner? = null
    var str_boleta: String? = null
    var str_nombre: String? = null
    var str_primerAp: String? = null
    var str_segundoAp: String? = null
    var str_carrera: String? = null
    var str_email: String? = null
    var str_contrasena: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrar_alumno)
        bindUI()
        //Toolbar
        toolb = findViewById<View>(R.id.toolbarregistraralumno) as Toolbar
        setSupportActionBar(toolb)
        //Spinner
        se = findViewById<View>(R.id.idspinner) as Spinner
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.combo_especialidad, R.layout.spinner_item_design
        )
        se!!.adapter = adapter
        se!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    parent.context,
                    "Selecionado: " + parent.getItemAtPosition(position).toString(),
                    Toast.LENGTH_LONG
                ).show()
                str_carrera = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun bindUI() {
        //Button
        buttonRegister = findViewById<View>(R.id.btnRegistrar) as Button
        //Edit Text
        editTextNumBoleta = findViewById<View>(R.id.editTextNumBoleta) as EditText
        editTextNombres = findViewById<View>(R.id.editTextNombres) as EditText
        editTextFirstName = findViewById<View>(R.id.editTextFirstName) as EditText
        editTextSecondName = findViewById<View>(R.id.editTextSecondName) as EditText
        editTextEmailAlumno = findViewById<View>(R.id.editTextEmailAlumno) as EditText
        editTextPasswordAlumno = findViewById<View>(R.id.editTextPass) as EditText
        editTextConfPasswordAlumno = findViewById<View>(R.id.editTextConfPassword) as EditText
    }
}