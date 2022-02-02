package com.example.prototipo2tt.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.prototipo2tt.R

class CloseLaboratoryActivity : AppCompatActivity() {
    lateinit var btnSendNotification: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_close_laboratory)

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
                    Toast.makeText(this, "¡Notificación enviada!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}