
package com.example.prototipo2tt.activities.attendant
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.prototipo2tt.PreferenceHelper
import com.example.prototipo2tt.PreferenceHelper.get
import com.example.prototipo2tt.R
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.io.response.AttendantReservationResponse
import com.example.prototipo2tt.models.LoadingDialogBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CloseLaboratoryActivity : AppCompatActivity() {
    private val apiService by lazy {

        ApiService.create()
    }

    private val preferences by lazy {

        PreferenceHelper.customPrefs(this,"jwt-attendant")

    }

    private lateinit var btnSendNotification: Button
    private lateinit var titleNotification: EditText
    private lateinit var messageNotification: EditText

    private lateinit var progressBar: LoadingDialogBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_close_laboratory)

        //Agregando el Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarCloseLaboratory)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        //Desplegando el boton hacia atras
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = LoadingDialogBar(this)

        bindUI()

        btnSendNotification.setOnClickListener {
            when {
                titleNotification.text.toString().isEmpty() -> {
                    titleNotification.error = "Se requiere un título"
                }
                messageNotification.text.toString().isEmpty() -> {
                    messageNotification.error = "Se requiere un mensaje"
                }
                else -> {
                    var title = titleNotification.text.toString()
                    var body = messageNotification.text.toString()
                    postSendNotification(title, body)
                }
            }
        }
    }

    private fun postSendNotification(title : String, body : String){
        progressBar.ShowDialog("Enviando...")
        val jwt = preferences["jwt-attendant",""]
        val authHeader = "Bearer $jwt"
        val call = apiService.postSendNotification(authHeader, title, body)
        call.enqueue(object : Callback<AttendantReservationResponse>{
            override fun onResponse(
                call: Call<AttendantReservationResponse>,
                response: Response<AttendantReservationResponse>
            ) {
                if (response.isSuccessful){
                    val notification = response.body()
                    if (notification?.success == true){

                        progressBar.HideDialog()
                        successSendNotification()

                    } else{

                        progressBar.HideDialog()
                        failSendNotification()
                    }
                }

            }

            override fun onFailure(call: Call<AttendantReservationResponse>, t: Throwable) {
                Toast.makeText(this@CloseLaboratoryActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun failSendNotification(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¡Ocurrio un problema al enviar la notificación!")
        builder.setPositiveButton("Volver") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()

    }

    private fun successSendNotification(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¡Tu notificación ha sido enviada!")
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun bindUI(){
        titleNotification = findViewById(R.id.editTextTitleNotification)
        messageNotification = findViewById(R.id.editTextMessageNotification)
        btnSendNotification = findViewById(R.id.btnSendNotification)
    }
}