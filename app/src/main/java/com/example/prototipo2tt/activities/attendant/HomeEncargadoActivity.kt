
package com.example.prototipo2tt.activities.attendant
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.cardview.widget.CardView
import android.os.Bundle
import com.example.prototipo2tt.R
import android.content.Intent
import androidx.core.view.GravityCompat
import android.annotation.SuppressLint
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import com.example.prototipo2tt.PreferenceHelper
import com.example.prototipo2tt.PreferenceHelper.set
import com.example.prototipo2tt.PreferenceHelper.get
import com.example.prototipo2tt.activities.ScheduleLaboratoryActivity
import com.example.prototipo2tt.activities.*
import com.example.prototipo2tt.activities.student.HomeAlumnoActivity
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.io.response.ProfileAttendantResponse
import com.example.prototipo2tt.models.LoadingDialogBar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeEncargadoActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val apiService by lazy {

        ApiService.create()
    }

    private val preferences by lazy {

        PreferenceHelper.customPrefs(this,"jwt-attendant")

    }

    private lateinit var drawer: DrawerLayout
    private lateinit  var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var cardViewReservation: CardView
    private lateinit var cardViewNotification: CardView
    private lateinit var cardViewGraphReports: CardView
    private lateinit var tvNombreEncargado: TextView
    private lateinit var tvPrimerApellidoEncargado : TextView
    private lateinit var tvSegundoApellidoEncargado: TextView
    private lateinit var tvLaboratorioEncargado: TextView

    private lateinit var progressBar:LoadingDialogBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_encargado)
        toolbar = findViewById(R.id.toolbarHomeEncargado)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        progressBar = LoadingDialogBar(this)

        drawer = findViewById(R.id.drawer_layout_profesor)
        val tg = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(tg)
        tg.syncState()

        navigationView = findViewById(R.id.nav_view_profesor)
        navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener(this)

        bindUI()

        val storeToken = intent.getBooleanExtra("store_token_attendant", false)
        if (storeToken){
            storeToken()
        }
        getProfileAttendant()

        cardViewReservation.setOnClickListener(View.OnClickListener {
            val intent1 =
                Intent(this@HomeEncargadoActivity, AttendantReservationActivity::class.java)
            startActivity(intent1)
        })
        cardViewNotification.setOnClickListener(View.OnClickListener {
            val intent2 = Intent(this@HomeEncargadoActivity, CloseLaboratoryActivity::class.java)
            startActivity(intent2)
        })
        cardViewGraphReports.setOnClickListener(View.OnClickListener {
            val intentGraphReservation =
                Intent(this@HomeEncargadoActivity, GraphReservationActivity::class.java)
            startActivity(intentGraphReservation)
        })
    }

    private fun storeToken(){
        val jwt = preferences["jwt-attendant",""]
        val authHeader = "Bearer $jwt"
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val deviceToken = task.result
            //Log.d("FCMService", deviceToken)
            val call = apiService.postTokenAttendant(authHeader, deviceToken)
            call.enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful){
                        Log.d(HomeEncargadoActivity.TAG, "Token registrado correctamente")
                    } else {
                        Log.d(HomeEncargadoActivity.TAG, "Hubo un problema al registrar el token")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@HomeEncargadoActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }

            })
        })
    }

    private fun getProfileAttendant(){

        progressBar.ShowDialog("Cargando...")
        val jwt = preferences["jwt-attendant",""]
        val call = apiService.profileAttendant("Bearer $jwt")
        call.enqueue(object : Callback<ProfileAttendantResponse> {
            override fun onResponse(
                call: Call<ProfileAttendantResponse>,
                response: Response<ProfileAttendantResponse>
            ) {
                if (response.isSuccessful){
                    val profileAttendant = response.body()
                    tvNombreEncargado.text = profileAttendant?.attendant?.name
                    tvPrimerApellidoEncargado.text = profileAttendant?.attendant?.first_name
                    tvSegundoApellidoEncargado.text = profileAttendant?.attendant?.second_name
                    tvLaboratorioEncargado.text = profileAttendant?.attendant?.laboratories?.get(0)?.name
                    progressBar.HideDialog()
                }
            }

            override fun onFailure(call: Call<ProfileAttendantResponse>, t: Throwable) {
                progressBar.HideDialog()
                Toast.makeText(this@HomeEncargadoActivity,
                    "Error al cargar la informaci??n del encargado.",
                    Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    @SuppressLint("NonConstantResourceId")
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menuScheduleLaboratories -> {
                val intentScheduleLaboratory =
                    Intent(this@HomeEncargadoActivity, ScheduleLaboratoryActivity::class.java)
                startActivity(intentScheduleLaboratory)
            }

            R.id.menuNotifications -> Toast.makeText(this, "Notificaciones", Toast.LENGTH_LONG)
                .show()

            R.id.menuReservationsAccepted -> {
                val intentAttendantReservationAccept =
                    Intent(this, AttendantReservationAcceptActivity::class.java)
                startActivity(intentAttendantReservationAccept)
            }

            R.id.menuReservationsHistory -> {
                val intentAttendantReservationHistory =
                    Intent(this, AttendantReservationHistoryActivity::class.java)
                startActivity(intentAttendantReservationHistory)
            }

            R.id.menuLogoutEncargado -> {
                postLogoutAttendant()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun postLogoutAttendant(){

        progressBar.ShowDialog("Cerrando sesi??n...")
        val jwt = preferences["jwt-attendant", ""]
        val call = apiService.postLogoutAttendant("Bearer $jwt")
        call.enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                progressBar.HideDialog()
                clearSessionPreference()
                val intent = Intent(this@HomeEncargadoActivity, LoginEncargadoActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                progressBar.HideDialog()
                Toast.makeText(this@HomeEncargadoActivity,
                    t.localizedMessage,
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun clearSessionPreference() {
        //Modificamos la variable session de la Preferencias para desactivar la sesion del usuario Encargado
        preferences["jwt-attendant"] = ""
    }

    private fun bindUI() {
        cardViewReservation = findViewById(R.id.cvReservations)
        cardViewNotification = findViewById(R.id.cvCloseLaboratory)
        cardViewGraphReports = findViewById(R.id.cvGraphReports)
        tvNombreEncargado = findViewById(R.id.tvNombreEncargado)
        tvPrimerApellidoEncargado = findViewById(R.id.tvPrimerApellidoEncargado)
        tvSegundoApellidoEncargado = findViewById(R.id.tvSegundoApellidoEncargado)
        tvLaboratorioEncargado = findViewById(R.id.tvLaboratorioEncargado)
    }

    companion object {
        private const val TAG = "HomeEncargadoActivity"
    }
}