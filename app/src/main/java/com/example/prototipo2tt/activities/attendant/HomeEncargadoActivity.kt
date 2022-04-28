
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
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.io.response.ProfileAttendantResponse
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_encargado)
        toolbar = findViewById(R.id.toolbarHomeEncargado)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

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

    private fun getProfileAttendant(){

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
                }
            }

            override fun onFailure(call: Call<ProfileAttendantResponse>, t: Throwable) {
                Toast.makeText(this@HomeEncargadoActivity,
                    "Error al cargar la información del encargado.",
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

        val jwt = preferences["jwt-attendant", ""]
        val call = apiService.postLogoutAttendant("Bearer $jwt")
        call.enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                clearSessionPreference()
                val intent = Intent(this@HomeEncargadoActivity, LoginEncargadoActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
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
}