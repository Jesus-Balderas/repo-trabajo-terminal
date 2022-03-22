package com.example.prototipo2tt.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.prototipo2tt.R
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

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
    var str_carrera: String? = null
    var carrer_id: Int? = 0

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

                str_carrera = parent.getItemAtPosition(position).toString()

                if (str_carrera == "ING. EN SISTEMAS COMPUTACIONALES") {
                    carrer_id = 1;
                } else
                    if (str_carrera == "ING. EN INTELIGENCIA ARTIFICIAL") {
                        carrer_id = 2;
                    } else
                        if (str_carrera == "LIC. EN CIENCIA DE DATOS") {
                            carrer_id = 3;
                        } else
                            if (str_carrera == "ING. EN SISTEMAS AUTOMOTRICES") {
                                carrer_id = 4;
                            } else
                                if (str_carrera == "M. EN C. EN SISTEMAS COMPUTACIONALES MÓVILES") {
                                    carrer_id = 5;
                                }
/*
                Toast.makeText(
                    parent.context,
                    "Id selecionado: " + carrer_id,
                    Toast.LENGTH_LONG
                ).show()
*/
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

    fun clickbtnInsertar(view: View) {
    /*Consultar primero si la boleta no ha sido registrada*/
        var url = "http://192.168.1.72/labscom/insertar.php"
        val queue = Volley.newRequestQueue(this)
        var resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->
                Toast.makeText(this, "Usuario insertado exitosamente", Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error $error ", Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                parametros.put("num_boleta", editTextNumBoleta?.text.toString())
                parametros.put("name", editTextNombres?.text.toString())
                parametros.put("first_name", editTextFirstName?.text.toString())
                parametros.put("second_name", editTextSecondName?.text.toString())
                parametros.put("email", editTextEmailAlumno?.text.toString())
                parametros.put("password", editTextPasswordAlumno?.text.toString())
                parametros.put("career_id", carrer_id.toString())
                return parametros
            }
        }
        queue.add(resultadoPost)
    }
}