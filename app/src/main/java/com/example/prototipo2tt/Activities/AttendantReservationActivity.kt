package com.example.prototipo2tt.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototipo2tt.Adapter.AttendantReservationAdapter
import com.example.prototipo2tt.Models.Reservation
import com.example.prototipo2tt.R
import java.io.Serializable

class AttendantReservationActivity : AppCompatActivity(),
    AttendantReservationAdapter.OnReservationClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendant_reservation)

        val recyclerView: RecyclerView = findViewById(R.id.reservationRecyclerView)

        //Configuracion del Adapter
        val adapter: AttendantReservationAdapter =
            AttendantReservationAdapter(this, reservations(), this)

        //Configuración del RecyclerView
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun reservations(): MutableList<Reservation> {

        val reservations: MutableList<Reservation> = ArrayList()
        reservations.add(
            Reservation(
                1, "AlumnoTest",
                "Laboratorio de Sistemas 1", 1, "08:00",
                "08:30", "2022-01-15", "Reservada"
            )
        )
        reservations.add(
            Reservation(
                2, "AlumnoTest",
                "Laboratorio de Sistemas 1", 2, "09:00",
                "09:30", "2022-01-15", "Reservada"
            )
        )
        reservations.add(
            Reservation(
                3, "AlumnoTest",
                "Laboratorio de Sistemas 1", 3, "10:00",
                "10:30", "2022-01-15", "Reservada"
            )
        )
        reservations.add(
            Reservation(
                4, "AlumnoTest",
                "Laboratorio de Sistemas 1", 4, "11:00",
                "11:30", "2022-01-15", "Reservada"
            )
        )
        reservations.add(
            Reservation(
                5, "AlumnoTest",
                "Laboratorio de Sistemas 1", 5, "12:00",
                "12:30", "2022-01-15", "Reservada"
            )
        )
        reservations.add(
            Reservation(
                6, "AlumnoTest",
                "Laboratorio de Sistemas 1", 6, "13:00",
                "13:30", "2022-01-15", "Reservada"
            )
        )
        reservations.add(
            Reservation(
                7, "AlumnoTest",
                "Laboratorio de Sistemas 1", 7, "08:00",
                "08:30", "2022-01-15", "Reservada"
            )
        )
        reservations.add(
            Reservation(
                8, "AlumnoTest",
                "Laboratorio de Sistemas 1", 8, "09:00",
                "09:30", "2022-01-15", "Reservada"
            )
        )
        reservations.add(
            Reservation(
                9, "AlumnoTest",
                "Laboratorio de Sistemas 1", 9, "10:00",
                "10:30", "2022-01-15", "Reservada"
            )
        )
        reservations.add(
            Reservation(
                10, "AlumnoTest",
                "Laboratorio de Sistemas 1", 10, "11:00",
                "11:30", "2022-01-15", "Reservada"
            )
        )
        return reservations
    }

    override fun onItemClick(reservation: Reservation) {

        val intent = Intent(this, DescriptionReservationActivity::class.java)
        intent.putExtra("Reservation", reservation as Serializable)
        startActivity(intent)
    }

}