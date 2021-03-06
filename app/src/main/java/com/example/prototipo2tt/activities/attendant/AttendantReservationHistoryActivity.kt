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
import com.example.prototipo2tt.adapter.AttendantReservationHistoryAdapter
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.models.AttendantReservation
import com.example.prototipo2tt.models.LoadingDialogBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AttendantReservationHistoryActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {

        PreferenceHelper.customPrefs(this,"jwt-attendant")

    }

    private val adapter = AttendantReservationHistoryAdapter(this)

    private lateinit var progressBar: LoadingDialogBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendant_reservation_history)

        //Agregando el Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarAttendantReservationHistory)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        //Desplegando el boton hacia atras
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = LoadingDialogBar(this)

        val rvAttendantReservationHistory : RecyclerView = findViewById(R.id.rvAttendantReservationHistory)
        rvAttendantReservationHistory.layoutManager = LinearLayoutManager(this)
        rvAttendantReservationHistory.adapter = adapter
        rvAttendantReservationHistory.hasFixedSize()

        getAttendantReservationsHistory()
    }

    private fun getAttendantReservationsHistory(){

        progressBar.ShowDialog("Cargando")

        val jwt = preferences["jwt-attendant",""]
        val call = apiService.getAttendantReservationsHistory("Bearer $jwt")
        call.enqueue(object : Callback<ArrayList<AttendantReservation>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ArrayList<AttendantReservation>>,
                response: Response<ArrayList<AttendantReservation>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        adapter.attendantReservHistory = it
                        adapter.notifyDataSetChanged()
                        progressBar.HideDialog()
                    }
                    if (response.body()?.isEmpty() == true){
                        progressBar.HideDialog()
                        emptyReservations()
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<AttendantReservation>>, t: Throwable) {
                progressBar.HideDialog()
                Toast.makeText(this@AttendantReservationHistoryActivity, "No se pudo cargar tu historial de reservaciones",
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun emptyReservations() {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Por el momento tu historial de reservaciones est?? vac??o.")
        builder.setPositiveButton("Ok") { _, _ ->
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }
}