package com.example.prototipo2tt.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.prototipo2tt.models.Reservation
import com.example.prototipo2tt.R
import com.example.prototipo2tt.models.AttendantReservation

class DescriptionReservationActivity : AppCompatActivity() {

    private lateinit var attendantReservation: AttendantReservation

    lateinit var btnRefuseRervation: Button
    lateinit var btnConfirmReservation: Button
    lateinit var btnConfirmRefuseReservation: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_reservation)
        //Agregando el Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarDescriptionReservation)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        //Desplegando el boton hacia atras
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val reservationId = findViewById<TextView>(R.id.txtViewIdReservacion)
        val student = findViewById<TextView>(R.id.txtViewAlumnoReservacion)
        val boleta = findViewById<TextView>(R.id.txtViewBoletaReservacion)
        val computer = findViewById<TextView>(R.id.txtViewComputadoraReservacion)
        val hour= findViewById<TextView>(R.id.txtViewHoraReservacion)
        val date = findViewById<TextView>(R.id.txtViewFechaReservacion)
        val status = findViewById<TextView>(R.id.txtViewEstadoReservacion)

        val reservationRefusedId = findViewById<TextView>(R.id.txtViewReservationRefusedId)
        val studentReservationRefused = findViewById<TextView>(R.id.txtViewReservationStudentRefused)

        val cvDetailsReservation = findViewById<CardView>(R.id.cvDetailsReservation)
        val cvRefusedReservation = findViewById<CardView>(R.id.cvRefuseReservation)

        val refusedReservation = findViewById<EditText>(R.id.editTextRefusedReservation)

        btnConfirmReservation = findViewById(R.id.btnConfirmarReservacion)
        btnConfirmReservation.setOnClickListener {
            Toast.makeText(this,
                "Confirmando Reservacion No: ${attendantReservation.id}",
                Toast.LENGTH_SHORT ).show()

        }

        btnConfirmRefuseReservation = findViewById(R.id.btnConfirmRefuseReservation)
        btnConfirmRefuseReservation.setOnClickListener {
            if (refusedReservation.text.toString().isEmpty()){
                refusedReservation.error = "Se requiere un motivo"
            } else {
                Toast.makeText(this, "¡Reservación rechazada exitosamente!", Toast.LENGTH_SHORT).show()

            }

        }

        btnRefuseRervation = findViewById(R.id.btnRechazarReservacion)
        btnRefuseRervation.setOnClickListener{
            //cvDetailsReservation.visibility = View.GONE
            //cvRefusedReservation.visibility = View.VISIBLE
            //reservationRefusedId.text = getString(R.string.description_reservation_id, attendantReservation.id)
            //studentReservationRefused.text = getString(R.string.description_reservation_alumno, attendantReservation.student.name)
            Toast.makeText(this,
                "Rechazando Reservacion No: ${attendantReservation.id}",
                Toast.LENGTH_SHORT ).show()
        }

        if (intent.extras != null){
            attendantReservation = intent.getSerializableExtra("Reservation") as AttendantReservation
            reservationId.text = getString(R.string.description_reservation_id, attendantReservation.id)
            student.text = getString(R.string.description_reservation_alumno, attendantReservation.student.name)
            boleta.text = getString(R.string.description_reservation_boleta, attendantReservation.student.num_boleta)
            computer.text = getString(R.string.description_reservation_computadora, attendantReservation.computer.num_pc)
            hour.text = getString(R.string.description_reservation_hora, attendantReservation.time)
            date.text = getString(R.string.description_reservation_Fecha, attendantReservation.date)
            status.text = getString(R.string.description_reservation_estado, attendantReservation.status)
        }
    }



}