package com.example.prototipo2tt.activities

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.prototipo2tt.R
import com.example.prototipo2tt.activities.student.LoginAlumnoActivity
import com.example.prototipo2tt.activities.attendant.LoginEncargadoActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var btnAlumno: Button
    private lateinit var btnEncargado: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindUI()
        val toolbar = findViewById<Toolbar>(R.id.toolbarmain)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

        btnAlumno.setOnClickListener {
            val itn = Intent(this@MainActivity, LoginAlumnoActivity::class.java)
            startActivity(itn)
        }
        btnEncargado.setOnClickListener {
            val itn2 = Intent(this@MainActivity, LoginEncargadoActivity::class.java)
            startActivity(itn2)
        }
    }

    private fun bindUI() {
        btnAlumno = findViewById(R.id.btnAlumno)
        btnEncargado = findViewById(R.id.btnEncargado)
    }
}