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
import com.example.prototipo2tt.adapter.StudentReservationAdapter
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.io.response.StudentReservationCancelResponse
import com.example.prototipo2tt.io.response.StudentReservationResponse
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
                    if (response.body()?.isEmpty() == true) {
                        emptyReservations()
                    }
                }

            }

            override fun onFailure(call: Call<ArrayList<StudentReservation>>, t: Throwable) {
                Toast.makeText(this@StudentReservationActivity, "No se puedieron cargar tus reservaciones",
                    Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun emptyReservations(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Por el momento no tienes solicitudes de reservaciones.")
        builder.setPositiveButton("Ok") { _, _ ->
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(studentReservation: StudentReservation) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Estás seguro que deseas cancelar tú reservación No.${studentReservation.id}?")
        builder.setPositiveButton("Si, cancelar") { _, _ ->
            val reservationId = studentReservation.id
            postCancelReservationStudent(reservationId)
        }
        builder.setNegativeButton("Volver") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun postCancelReservationStudent(reservationId : Int){
        val jwt = preferences["jwt-student",""]
        val call = apiService.postCancelReservationStudent("Bearer $jwt", reservationId)
        call.enqueue(object : Callback<StudentReservationCancelResponse>{
            override fun onResponse(
                call: Call<StudentReservationCancelResponse>,
                response: Response<StudentReservationCancelResponse>
            ) {
                if (response.isSuccessful){
                    val reservationCancel = response.body()
                    if (reservationCancel?.success == true){

                        successCancelReservation()
                    }
                    else {
                        Toast.makeText(this@StudentReservationActivity,
                            "¡La reservación no se pudo cancelar!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<StudentReservationCancelResponse>, t: Throwable) {
                Toast.makeText(this@StudentReservationActivity, "No se pudo cancelar la reservación",
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun successCancelReservation(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¡La reservación se ha cancelado exitosamente!")
        builder.setPositiveButton("Ok") { dialog, _ ->
            getStudentReservations()
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

}