
package com.example.prototipo2tt.activities.student

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
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.io.response.ProfileStudentResponse
import com.example.prototipo2tt.models.LoadingDialogBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeAlumnoActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val apiService by lazy {

        ApiService.create()
    }

    private val preferences by lazy {

        PreferenceHelper.customPrefs(this,"jwt-student")

    }
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var cardViewCreateReservation: CardView
    private lateinit var cvMyReservations: CardView
    private lateinit var cvLaboratoriesPDF: CardView
    private lateinit var tvNumBoleta: TextView
    private lateinit var tvName: TextView
    private lateinit var tvFirstName: TextView
    private lateinit var tvSecondName: TextView
    private lateinit var tvCarrera: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_alumno)
        toolbar = findViewById(R.id.toolbarHomeAlumno)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView = findViewById(R.id.nav_view)
        navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener(this)
        bindUI()

        getProfileStudent()

        cardViewCreateReservation.setOnClickListener(View.OnClickListener {
            val intentReservation =
                Intent(this@HomeAlumnoActivity, CreateReservationActivity::class.java)
            startActivity(intentReservation)
        })

        cvMyReservations.setOnClickListener {
            val intentMyReservations =
                Intent(this, StudentReservationActivity::class.java)
            startActivity(intentMyReservations)
        }

        cvLaboratoriesPDF.setOnClickListener {
            val intentLaboratoriesPDF =
                Intent(this, LaboratoriesPDFActivity::class.java)
            startActivity(intentLaboratoriesPDF)
        }

    }

    private fun getProfileStudent(){
        val progressBar = LoadingDialogBar(this)
        progressBar.ShowDialog("Cargando...")
        val jwt = preferences["jwt-student",""]
        val call = apiService.profileStudent("Bearer $jwt")
        call.enqueue(object : Callback<ProfileStudentResponse>{
            override fun onResponse(call: Call<ProfileStudentResponse>, response: Response<ProfileStudentResponse>) {
                if (response.isSuccessful){
                    val profileStudent = response.body()
                    tvNumBoleta.text = profileStudent?.student?.num_boleta
                    tvName.text = profileStudent?.student?.name
                    tvFirstName.text = profileStudent?.student?.first_name
                    tvSecondName.text = profileStudent?.student?.second_name
                    tvCarrera.text = profileStudent?.student?.career?.name
                    progressBar.HideDialog()
                }
            }

            override fun onFailure(call: Call<ProfileStudentResponse>, t: Throwable) {
                progressBar.HideDialog()
                Toast.makeText(this@HomeAlumnoActivity,
                    "Error al cargar la información del estudiante.",
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun bindUI(){
        cardViewCreateReservation = findViewById(R.id.cvCreateReservation)
        cvMyReservations = findViewById(R.id.cvMyReservations)
        cvLaboratoriesPDF = findViewById(R.id.cvLaboratoriesPDF)
        tvNumBoleta = findViewById(R.id.tvNumBoleta)
        tvName = findViewById(R.id.tvNombreAlumno)
        tvFirstName = findViewById(R.id.tvPrimerApellidoAlumno)
        tvSecondName = findViewById(R.id.tvSegundoApellidoAlumno)
        tvCarrera = findViewById(R.id.tvCarreraAlumno)
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
            R.id.id_menuLaboratoriosAlumno -> {
                val intentScheduleLaboratory =
                    Intent(this@HomeAlumnoActivity, ScheduleLaboratoryActivity::class.java)
                startActivity(intentScheduleLaboratory)
            }
            R.id.menuMisReservasHistorialAlumno -> {
                val intentStudentReservationHistory =
                    Intent(this,StudentReservationHistoryActivity::class.java)
                startActivity(intentStudentReservationHistory)
            }

            R.id.menuMisReservasAceptadasAlumno -> {
                val intentStudentReservationAccept =
                    Intent(this, StudentReservationAcceptActivity::class.java)
                startActivity(intentStudentReservationAccept)
            }

            R.id.id_menuLogoutAlumno -> {

                postLogoutStudent()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun postLogoutStudent(){
        val progressBar = LoadingDialogBar(this)
        progressBar.ShowDialog("Cerrando sesión...")
        val jwt = preferences["jwt-student",""]

        val call = apiService.postLogoutStudent("Bearer $jwt")
        call.enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                progressBar.HideDialog()
                clearSessionPreference()
                val intent = Intent(this@HomeAlumnoActivity, LoginAlumnoActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@HomeAlumnoActivity,
                    t.localizedMessage,
                    Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun clearSessionPreference() {
        //Modificamos la variable session de la Preferencias para desactivar la sesion del usuario Alumno
        preferences["jwt-student"] = ""
    }
}