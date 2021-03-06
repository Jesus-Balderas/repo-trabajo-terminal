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
import com.example.prototipo2tt.io.response.AttendantReservationResponse
import com.example.prototipo2tt.models.AttendantReservation
import com.example.prototipo2tt.models.LoadingDialogBar
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

    private lateinit var progressBar: LoadingDialogBar

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

        progressBar = LoadingDialogBar(this)

        val recyclerView : RecyclerView = findViewById(R.id.rvAttendantReservationAccept)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()

        getAttendantReservationsAccepted()

    }

    private fun getAttendantReservationsAccepted(){

        progressBar.ShowDialog("Cargando...")
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
                        progressBar.HideDialog()
                    }
                    if (response.body()?.isEmpty() == true){
                        progressBar.HideDialog()
                        emptyReservationsAccept()
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<AttendantReservation>>, t: Throwable) {
                progressBar.HideDialog()
                Toast.makeText(this@AttendantReservationAcceptActivity, "No se pudo cargar tu historial de reservaciones aceptadas",
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun emptyReservationsAccept(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Por el momento no tienes solicitudes de reservaciones aceptadas.")
        builder.setPositiveButton("Ok") { _, _ ->
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onItemClick(attendantReservAccept: AttendantReservation) {
        val reservationId = attendantReservAccept.id
        val builder = AlertDialog.Builder(this)
        builder.setMessage("??Deseas finalizar la reservaci??n No.$reservationId?")
        builder.setPositiveButton("Si, finalizar") { _, _ ->
            finishReservation(reservationId)
        }
        builder.setNegativeButton("Volver") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun finishReservation(reservationId : Int){

        progressBar.ShowDialog("Finalizando...")
        val jwt = preferences["jwt-attendant",""]
        val call = apiService.postFinishReservationAttendant("Bearer $jwt", reservationId)
        call.enqueue(object : Callback<AttendantReservationResponse> {
            override fun onResponse(
                call: Call<AttendantReservationResponse>,
                response: Response<AttendantReservationResponse>
            ) {
                if (response.isSuccessful){
                    val finish = response.body()
                    if (finish?.success == true){
                        progressBar.HideDialog()
                        successFinishReservation()
                    }
                }
            }

            override fun onFailure(call: Call<AttendantReservationResponse>, t: Throwable) {
                progressBar.HideDialog()
                Toast.makeText(this@AttendantReservationAcceptActivity, "Ocurrio un problema al finalizar la reservaci??n.",
                    Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun successFinishReservation(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("??La reservaci??n se ha finalizado exitosamente!")
        builder.setPositiveButton("Ok") { dialog, _ ->
            getAttendantReservationsAccepted()
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}