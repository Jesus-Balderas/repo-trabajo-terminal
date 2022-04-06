package com.example.prototipo2tt.activities

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
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import com.example.prototipo2tt.PreferenceHelper
import com.example.prototipo2tt.PreferenceHelper.set

class HomeAlumnoActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var cardViewCreateReservation: CardView

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

        cardViewCreateReservation = findViewById(R.id.cvCreateReservation)
        cardViewCreateReservation.setOnClickListener(View.OnClickListener {
            val intentReservation =
                Intent(this@HomeAlumnoActivity, CreateReservationActivity::class.java)
            startActivity(intentReservation)
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
            R.id.id_menuLaboratoriosAlumno -> {
                val intentScheduleLaboratory =
                    Intent(this@HomeAlumnoActivity, ScheduleLaboratoryActivity::class.java)
                startActivity(intentScheduleLaboratory)
            }
            R.id.id_menuMisReservasAlumnoCanceladas -> Toast.makeText(
                this,
                "Canceladas",
                Toast.LENGTH_SHORT
            ).show()
            R.id.id_menuMisReservasAlumnoAceptadas -> Toast.makeText(
                this,
                "Aceptadas",
                Toast.LENGTH_SHORT
            ).show()
            R.id.id_menuLogoutAlumno -> {
                clearSessionPreference()
                val intent = Intent(this, LoginAlumnoActivity::class.java)
                startActivity(intent)
                finish()

            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun clearSessionPreference() {
        //Accedemos a las Preferencias
        val preferences = PreferenceHelper.customPrefs(this,"session-student")
        //Modificamos la variable session de la Preferencias para desactivar la sesion del usuario Alumno
        preferences["session-student"] = false
    }
}