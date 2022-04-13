package com.example.prototipo2tt.activities.student

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototipo2tt.PreferenceHelper
import com.example.prototipo2tt.PreferenceHelper.get
import com.example.prototipo2tt.R
import com.example.prototipo2tt.adapter.StudentReservationAdapter
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.models.StudentReservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentReservationActivity : AppCompatActivity(),
    StudentReservationAdapter.OnStudentReservationClickListener {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {

        PreferenceHelper.customPrefs(this,"jwt-student")

    }

    private val adapter = StudentReservationAdapter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_reservation)

        //Agregando el Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarStudentReservation)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        //Desplegando el boton hacia atras
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val rvStudentReservation : RecyclerView = findViewById(R.id.rvStudentReservation)
        rvStudentReservation.layoutManager = LinearLayoutManager(this)
        rvStudentReservation.adapter = adapter
        rvStudentReservation.hasFixedSize()

        getStudentReservations()


    }

    private fun getStudentReservations() {

        val jwt = preferences["jwt-student",""]
        val call = apiService.getStudentReservations("Bearer $jwt")
        call.enqueue(object : Callback<ArrayList<StudentReservation>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ArrayList<StudentReservation>>,
                response: Response<ArrayList<StudentReservation>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        adapter.studentReservation = it
                        adapter.notifyDataSetChanged()
                    }
                }

            }

            override fun onFailure(call: Call<ArrayList<StudentReservation>>, t: Throwable) {
                Toast.makeText(this@StudentReservationActivity, "No se puedieron cargar tus reservaciones",
                    Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onItemClick(studentReservation: StudentReservation) {
        Toast.makeText(this, "Cancelar reservación No. ${studentReservation.id}", Toast.LENGTH_SHORT).show()
    }
}