package com.example.prototipo2tt.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototipo2tt.adapter.AttendantReservationAdapter
import com.example.prototipo2tt.models.Reservation
import com.example.prototipo2tt.R
import com.example.prototipo2tt.io.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class AttendantReservationActivity : AppCompatActivity(),
    AttendantReservationAdapter.OnReservationClickListener {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }
    //Configuracion del Adapter
    private val adapter = AttendantReservationAdapter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendant_reservation)

        //Agregando el Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_profesor)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        //Desplegando el boton hacia atras
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //Configuración del RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.reservationRecyclerView)

        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        getJSONReservations()

    }

    private fun getJSONReservations() {

        val attendantId = 1
        val call = apiService.getAttendantReservations(attendantId)
        call.enqueue(object : Callback<ArrayList<Reservation>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ArrayList<Reservation>>,
                response: Response<ArrayList<Reservation>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        adapter.reservation = it
                        adapter.notifyDataSetChanged()
                    }
                }

            }

            override fun onFailure(call: Call<ArrayList<Reservation>>, t: Throwable) {
                Toast.makeText(this@AttendantReservationActivity, "No se puedieron cargar las reservaciones",
                    Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onItemClick(reservation: Reservation) {

        val intent = Intent(this, DescriptionReservationActivity::class.java)
        intent.putExtra("Reservation", reservation as Serializable)
        startActivity(intent)
    }

}