package com.example.prototipo2tt.activities.attendant

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototipo2tt.PreferenceHelper
import com.example.prototipo2tt.PreferenceHelper.get
import com.example.prototipo2tt.adapter.AttendantReservationAdapter
import com.example.prototipo2tt.R
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.models.AttendantReservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class AttendantReservationActivity : AppCompatActivity(),
    AttendantReservationAdapter.OnReservationClickListener {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {

        PreferenceHelper.customPrefs(this,"jwt-attendant")

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

        val jwt = preferences["jwt-attendant",""]
        val call = apiService.getAttendantReservations("Bearer $jwt")
        call.enqueue(object : Callback<ArrayList<AttendantReservation>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ArrayList<AttendantReservation>>,
                response: Response<ArrayList<AttendantReservation>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        adapter.attendantReservation = it
                        adapter.notifyDataSetChanged()
                    }
                    if (response.body()?.isEmpty() == true){
                        emptyReservations()
                    }
                }


            }

            override fun onFailure(call: Call<ArrayList<AttendantReservation>>, t: Throwable) {
                Toast.makeText(this@AttendantReservationActivity, "No se puedieron cargar las reservaciones",
                    Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun emptyReservations() {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Por el momento no tienes buzón de solicitudes de reservaciones.")
        builder.setPositiveButton("Ok") { _, _ ->
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onItemClick(attendantReservation: AttendantReservation) {

        val intent = Intent(this, DescriptionReservationActivity::class.java)
        intent.putExtra("Reservation", attendantReservation as Serializable)
        startActivity(intent)
    }

}