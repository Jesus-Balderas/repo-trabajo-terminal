package com.example.prototipo2tt.activities

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import com.example.prototipo2tt.R
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.prototipo2tt.activities.registrar_alumno
import com.example.prototipo2tt.activities.alumno_home
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class login private constructor(boleta: String, password: String) : AppCompatActivity() {
    var btnRegister: Button? = null
    var btnLogin: Button? = null
    var btnForgetPassword: Button? = null
    var editTextBoleta: EditText? = null
    var editTextPassword: EditText? = null
    var toolb: Toolbar? = null
    var contrasenaDevuelta: String? = null


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
/*
    private fun goToAlumnoHome() {
        val intent1 = Intent(this@login, alumno_home::class.java)
        intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent1)
    }*/

    fun clickbtnIngresar(view: View){
        var num_boleta: String? = editTextBoleta?.text.toString()

        Toast.makeText(this,"num_boleta: " + num_boleta,Toast.LENGTH_LONG).show()

/*
        val queue= Volley.newRequestQueue(this)
        val url="http://192.168.1.72/labscom/consulta.php?id=$num_boleta"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener { response ->
                contrasenaDevuelta = response.getString("password")
            },Response.ErrorListener { error ->
                Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
        */

    }


}