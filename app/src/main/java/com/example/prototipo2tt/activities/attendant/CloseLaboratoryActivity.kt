package com.example.prototipo2tt.activities.attendant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.prototipo2tt.R

class CloseLaboratoryActivity : AppCompatActivity() {
    lateinit var btnSendNotification: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_close_laboratory)

        //Agregando el Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarCloseLaboratory)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        //Desplegando el boton hacia atras
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val titleNotification = findViewById<EditText>(R.id.editTextTitleNotification)
        val messageNotification = findViewById<EditText>(R.id.editTextMessageNotification)

        btnSendNotification = findViewById(R.id.btnSendNotification)
        btnSendNotification.setOnClickListener {
            when {
                titleNotification.text.toString().isEmpty() -> {
                    titleNotification.error = "Se requiere un título"
                }
                messageNotification.text.toString().isEmpty() -> {
                    messageNotification.error = "Se requiere un mensaje"
                }
                else -> {
                    Toast.makeText(this, "¡Notificación enviada!", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }
}