package com.example.prototipo2tt.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.prototipo2tt.R;

public class HomeEncargadoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout dl;
    NavigationView nv;
    Toolbar toolb;
    CardView cardViewReservation, cardViewNotification, cardViewGraphReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_encargado);
        dl = findViewById(R.id.drawer_layout_profesor);
        nv = findViewById(R.id.nav_view_profesor);

        toolb = findViewById(R.id.toolbar_profesor);
        setSupportActionBar(toolb);

        cardViewReservation = findViewById(R.id.cvReservations);
        cardViewNotification = findViewById(R.id.cvCloseLaboratory);
        cardViewGraphReports = findViewById(R.id.cvGraphReports);

        //hide and show items
        Menu menu = nv.getMenu();
        //menu.findItem(R.id.id_menuloginprofesor).setVisible(false);

        nv.bringToFront();
        ActionBarDrawerToggle tg = new ActionBarDrawerToggle(this, dl, toolb, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(tg);
        tg.syncState();

        nv.setNavigationItemSelectedListener(this);
        //nv.setCheckedItem(R.id.menuScheduleLaboratories);

        cardViewReservation.setOnClickListener(v -> {
            Intent intent1 = new Intent(HomeEncargadoActivity.this, AttendantReservationActivity.class);
            startActivity(intent1);
        });

        cardViewNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(HomeEncargadoActivity.this, CloseLaboratoryActivity.class);
                startActivity(intent2);
            }
        });

        cardViewGraphReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGraphReservation = new Intent(HomeEncargadoActivity.this, GraphReservationActivity.class);
                startActivity(intentGraphReservation);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menuScheduleLaboratories:
                Intent intentScheduleLaboratory = new Intent(HomeEncargadoActivity.this, ScheduleLaboratoryActivity.class);
                startActivity(intentScheduleLaboratory);
                break;
            case R.id.menuNotifications:
                Toast.makeText(this, "Notificaciones", Toast.LENGTH_LONG).show();
                break;
            case R.id.menuConfirmedReservations:
                Toast.makeText(this, "Reservaciones Confirmadas", Toast.LENGTH_LONG).show();
                break;
            case R.id.menuRefusedReservations:
                Toast.makeText(this, "Reservaciones Rechazadas", Toast.LENGTH_LONG).show();
                break;
            case R.id.id_menulogoutprofesor:
                Toast.makeText(this, "Cerrar Sesión", Toast.LENGTH_SHORT).show();
                break;
        }
        dl.closeDrawer(GravityCompat.START);
        return true;
    }
}
