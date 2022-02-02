package com.example.prototipo2tt.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.prototipo2tt.models.Reservation
import com.example.prototipo2tt.R

class DescriptionReservationActivity : AppCompatActivity() {

    private lateinit var reservation: Reservation

    lateinit var btnRefuseRervation: Button
    lateinit var btnConfirmReservation: Button
    lateinit var btnConfirmRefuseReservation: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_reservation)

        val reservationId = findViewById<TextView>(R.id.txtViewIdReservacion)
        val student = findViewById<TextView>(R.id.txtViewAlumnoReservacion)
        val laboratory = findViewById<TextView>(R.id.txtViewLabReservacion)
        val computer = findViewById<TextView>(R.id.txtViewComputadoraReservacion)
        val hourStart = findViewById<TextView>(R.id.txtViewHoraInicioReservacion)
        val hourEnd = findViewById<TextView>(R.id.txtViewHoraFinReservacion)
        val date = findViewById<TextView>(R.id.txtViewFechaReservacion)
        val status = findViewById<TextView>(R.id.txtViewEstadoReservacion)

        val reservationRefusedId = findViewById<TextView>(R.id.txtViewReservationRefusedId)
        val studentReservationRefused = findViewById<TextView>(R.id.txtViewReservationStudentRefused)

        val cvDetailsReservation = findViewById<CardView>(R.id.cvDetailsReservation)
        val cvRefusedReservation = findViewById<CardView>(R.id.cvRefuseReservation)

        val refusedReservation = findViewById<EditText>(R.id.editTextRefusedReservation)

        btnConfirmReservation = findViewById(R.id.btnConfirmarReservacion)
        btnConfirmReservation.setOnClickListener {
            Toast.makeText(this, "¡Reservación confirmada exitosamente!", Toast.LENGTH_SHORT ).show()
            finish()
        }

        btnConfirmRefuseReservation = findViewById(R.id.btnConfirmRefuseReservation)
        btnConfirmRefuseReservation.setOnClickListener {
            if (refusedReservation.text.toString().isEmpty()){
                refusedReservation.error = "Se requiere un motivo"
            } else {
                Toast.makeText(this, "¡Reservación rechazada exitosamente!", Toast.LENGTH_SHORT).show()
                finish()
            }

        }

        btnRefuseRervation = findViewById(R.id.btnRechazarReservacion)
        btnRefuseRervation.setOnClickListener{
            cvDetailsReservation.visibility = View.GONE
            cvRefusedReservation.visibility = View.VISIBLE
            reservationRefusedId.text = getString(R.string.description_reservation_id, reservation.id)
            studentReservationRefused.text = getString(R.string.description_reservation_alumno, reservation.student)
        }

        if (intent.extras != null){
            reservation = intent.getSerializableExtra("Reservation") as Reservation
            reservationId.text = getString(R.string.description_reservation_id, reservation.id)
            student.text = getString(R.string.description_reservation_alumno, reservation.student)
            laboratory.text = getString(R.string.description_reservation_laboratorio, reservation.laboratory)
            computer.text = getString(R.string.description_reservation_computadora, reservation.computer)
            hourStart.text = getString(R.string.description_reservation_horaInicio, reservation.hourStart)
            hourEnd.text = getString(R.string.description_reservation_horaFin, reservation.hourEnd)
            date.text = getString(R.string.description_reservation_Fecha, reservation.date)
            status.text = getString(R.string.description_reservation_estado, reservation.status)
        }
    }



}