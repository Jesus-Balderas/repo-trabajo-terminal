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

class HomeEncargadoActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit  var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var cardViewReservation: CardView
    private lateinit var cardViewNotification: CardView
    private lateinit var cardViewGraphReports: CardView

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

        cardViewReservation = findViewById(R.id.cvReservations)
        cardViewNotification = findViewById(R.id.cvCloseLaboratory)
        cardViewGraphReports = findViewById(R.id.cvGraphReports)


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
            R.id.menuConfirmedReservations -> Toast.makeText(
                this,
                "Reservaciones Confirmadas",
                Toast.LENGTH_LONG
            ).show()
            R.id.menuRefusedReservations -> Toast.makeText(
                this,
                "Reservaciones Rechazadas",
                Toast.LENGTH_LONG
            ).show()
            R.id.menuLogoutEncargado -> {
                clearSessionPreference()
                val intent = Intent(this, LoginEncargadoActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun clearSessionPreference() {
        //Accedemos a las Preferencias
        val preferences = PreferenceHelper.customPrefs(this, "session-profesor")
        //Modificamos la variable session de la Preferencias para desactivar la sesion del usuario Alumno
        preferences["session-profesor"] = false
    }
}