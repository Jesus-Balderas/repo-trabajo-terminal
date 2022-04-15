package com.example.prototipo2tt.activities.attendant

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototipo2tt.PreferenceHelper
import com.example.prototipo2tt.PreferenceHelper.get
import com.example.prototipo2tt.R
import com.example.prototipo2tt.adapter.AttendantReservationAcceptAdapter
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.models.AttendantReservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AttendantReservationAcceptActivity : AppCompatActivity(),
    AttendantReservationAcceptAdapter.OnReservationAcceptClickListener{

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {

        PreferenceHelper.customPrefs(this,"jwt-attendant")

    }

    private val adapter = AttendantReservationAcceptAdapter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendant_reservation_accept)

        //Agregando el Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarAttendantReservationAccept)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        //Desplegando el boton hacia atras
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView : RecyclerView = findViewById(R.id.rvAttendantReservationAccept)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()

        getAttendantReservationsAccepted()

    }

    private fun getAttendantReservationsAccepted(){

        val jwt = preferences["jwt-attendant",""]
        val call = apiService.getAttendantReservationsAccepted("Bearer $jwt")
        call.enqueue(object : Callback<ArrayList<AttendantReservation>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ArrayList<AttendantReservation>>,
                response: Response<ArrayList<AttendantReservation>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        adapter.attendantReservAccept = it
                        adapter.notifyDataSetChanged()
                    }
                    if (response.body()?.isEmpty() == true){
                        emptyReservationsAccept()
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<AttendantReservation>>, t: Throwable) {
                Toast.makeText(this@AttendantReservationAcceptActivity, "No se pudo cargar tu historial de reservaciones aceptadas",
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun emptyReservationsAccept(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Reservaciones Aceptadas")
        builder.setMessage("No tienes reservaciones por aceptar por el momento.")
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onItemClick(attendantReservAccept: AttendantReservation) {
        Toast.makeText(this, "Finalizar Reservacion: ${attendantReservAccept.id}",
        Toast.LENGTH_SHORT).show()
    }
}