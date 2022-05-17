package com.example.prototipo2tt.activities.attendant

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.prototipo2tt.PreferenceHelper
import com.example.prototipo2tt.PreferenceHelper.get
import com.example.prototipo2tt.R
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.io.response.AttendantReservationResponse
import com.example.prototipo2tt.models.AttendantReservation
import com.example.prototipo2tt.models.LoadingDialogBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescriptionReservationActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {

        PreferenceHelper.customPrefs(this,"jwt-attendant")

    }

    private lateinit var reservationId : TextView
    private lateinit var student : TextView
    private lateinit var boleta : TextView
    private lateinit var computer : TextView
    private lateinit var hour : TextView
    private lateinit var date : TextView
    private lateinit var status : TextView

    private lateinit var progressBar: LoadingDialogBar


    private lateinit var btnRefuseRervation: Button
    private lateinit var btnConfirmReservation: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_reservation)

        //Agregando el Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarDescriptionReservation)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        //Desplegando el boton hacia atras
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = LoadingDialogBar(this)

        bindUI()
        var attendantReservation: AttendantReservation? = null
        if (intent.extras != null){
            attendantReservation = intent.getSerializableExtra("Reservation") as AttendantReservation
            reservationId.text = attendantReservation.id.toString()
            student.text = attendantReservation.student.name
            boleta.text = attendantReservation.student.num_boleta
            computer.text = attendantReservation.computer.num_pc.toString()
            hour.text = attendantReservation.time
            date.text = attendantReservation.date
            status.text = attendantReservation.status
        }

        btnConfirmReservation.setOnClickListener {

            attendantReservation?.id?.let {
                    it1 -> confirmReservation(it1)
            }

        }

        btnRefuseRervation.setOnClickListener{

            attendantReservation?.id?.let {
                it2 -> refuseReservation(it2)
            }

        }

    }

    private fun bindUI(){
        reservationId = findViewById(R.id.tvIdReservation)
        student = findViewById(R.id.tvAlumnoReservacion)
        boleta = findViewById(R.id.txtViewBoletaReservacion)
        computer = findViewById(R.id.tvComputadoraReservacion)
        hour= findViewById(R.id.tvHoraReservacion)
        date = findViewById(R.id.tvFechaReservacion)
        status = findViewById(R.id.tvEstadoReservacion)
        btnConfirmReservation = findViewById(R.id.btnConfirmarReservacion)
        btnRefuseRervation = findViewById(R.id.btnRechazarReservacion)

    }

    private fun refuseReservation(reservationId : Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Deseas rechazar la reservación No. $reservationId?")
        builder.setPositiveButton("Si, rechazar") { _, _ ->

            rejectReservation(reservationId)
        }
        builder.setNegativeButton("Volver") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()

    }

    private fun confirmReservation(reservationId : Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Deseas aceptar la reservación No. $reservationId?")
        builder.setPositiveButton("Si, aceptar") { _, _ ->

            acceptReservation(reservationId)
        }
        builder.setNegativeButton("Volver") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun rejectReservation(reservationId: Int){
        progressBar.ShowDialog("Rechazando...")
        val jwt = preferences["jwt-attendant",""]
        val call = apiService.postRejectReservationAttendant("Bearer $jwt", reservationId)
        call.enqueue(object : Callback<AttendantReservationResponse>{
            override fun onResponse(
                call: Call<AttendantReservationResponse>,
                response: Response<AttendantReservationResponse>
            ) {
                if (response.isSuccessful){
                    val reject = response.body()
                    if (reject?.success == true){
                        progressBar.HideDialog()
                        showDialogRejectReservation()
                    }
                }
            }

            override fun onFailure(call: Call<AttendantReservationResponse>, t: Throwable) {
                progressBar.HideDialog()
                Toast.makeText(this@DescriptionReservationActivity, "Ocurrio un problema al rechazar la reservación",
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showDialogRejectReservation(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¡Haz rechazado la reservación exitosamente!")
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
            val intent = Intent(this@DescriptionReservationActivity, AttendantReservationActivity::class.java)
            startActivity(intent)
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun acceptReservation(reservationId: Int){
        progressBar.ShowDialog("Aceptando...")
        val jwt = preferences["jwt-attendant",""]
        val call = apiService.postAcceptReservationAttendant("Bearer $jwt", reservationId)
        call.enqueue(object: Callback<AttendantReservationResponse>{
            override fun onResponse(
                call: Call<AttendantReservationResponse>,
                response: Response<AttendantReservationResponse>
            ) {
                if (response.isSuccessful){
                    val accept = response.body()
                    if (accept?.success == true){
                        progressBar.HideDialog()
                        showDialogAcceptReservation()
                    }
                }
            }

            override fun onFailure(call: Call<AttendantReservationResponse>, t: Throwable) {
                progressBar.HideDialog()
                Toast.makeText(this@DescriptionReservationActivity, "Ocurrio un problema al aceptar la reservación",
                    Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun showDialogAcceptReservation(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¡Haz aceptado la reservación exitosamente!")
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
            val intent = Intent(this@DescriptionReservationActivity, AttendantReservationActivity::class.java)
            startActivity(intent)
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }

}