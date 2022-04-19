package com.example.prototipo2tt.activities.student

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
import com.example.prototipo2tt.adapter.StudentReservationAcceptAdapter
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.models.StudentReservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentReservationAcceptActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {

        PreferenceHelper.customPrefs(this,"jwt-student")

    }

    private val adapter = StudentReservationAcceptAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_reservation_accept)

        //Agregando el Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarStudentReservationAccept)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        //Desplegando el boton hacia atras
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val rvStudentReservationAccept : RecyclerView = findViewById(R.id.rvStudentReservationAccept)
        rvStudentReservationAccept.layoutManager = LinearLayoutManager(this)
        rvStudentReservationAccept.adapter = adapter
        rvStudentReservationAccept.hasFixedSize()

        getStudentReservationsAccept()
    }

    private fun getStudentReservationsAccept(){
        val jwt = preferences["jwt-student",""]
        val call = apiService.getStudentReservationsAccept("Bearer $jwt")
        call.enqueue(object : Callback<ArrayList<StudentReservation>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ArrayList<StudentReservation>>,
                response: Response<ArrayList<StudentReservation>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        adapter.stdReservationAccept = it
                        adapter.notifyDataSetChanged()
                    }
                    if (response.body()?.isEmpty() == true){
                        emptyReservationsAccept()
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<StudentReservation>>, t: Throwable) {
                Toast.makeText(this@StudentReservationAcceptActivity, "No se pudo cargar tus reservaciones aceptadas",
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
}