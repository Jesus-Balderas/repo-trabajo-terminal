package com.example.prototipo2tt.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototipo2tt.adapter.AttendantReservationAdapter
import com.example.prototipo2tt.models.Reservation
import com.example.prototipo2tt.R
import java.io.Serializable

class AttendantReservationActivity : AppCompatActivity(),
    AttendantReservationAdapter.OnReservationClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendant_reservation)

        //Configuración del RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.reservationRecyclerView)

        //Configuracion del Adapter
        val adapter = AttendantReservationAdapter(this, reservations(), this)


        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    private fun reservations(): ArrayList<Reservation> {

        val reservations = ArrayList<Reservation>()
        reservations.add(
            Reservation(
                1, "123456789",
                "Alumno Test", 1, "Reservada",
                "2022-01-15", "13:30"
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