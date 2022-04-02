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

public class HomeAlumnoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dl;
    NavigationView nv;
    Toolbar toolb;
    CardView cardViewCreateReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_alumno);

        dl = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        toolb = findViewById(R.id.toolbar);

        cardViewCreateReservation = findViewById(R.id.cvCreateReservation);

        setSupportActionBar(toolb);

        //hide and show items
        Menu menu = nv.getMenu();
        //menu.findItem(R.id.id_menuloginAlumno).setVisible(false);

        nv.bringToFront();
        ActionBarDrawerToggle tg = new ActionBarDrawerToggle(this, dl, toolb, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(tg);
        tg.syncState();

        nv.setNavigationItemSelectedListener(this);
        //nv.setCheckedItem(R.id.id_menuhorario);

        cardViewCreateReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReservation = new Intent(HomeAlumnoActivity.this, CreateReservationActivity.class);
                startActivity(intentReservation);
            }
        });

    }

    @Override
    public void onBackPressed(){
        if(dl.isDrawerOpen(GravityCompat.START)){
            dl.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.id_menuLaboratoriosAlumno:
                Intent intentScheduleLaboratory = new Intent(HomeAlumnoActivity.this, ScheduleLaboratoryActivity.class);
                startActivity(intentScheduleLaboratory);
                break;
            case R.id.id_menuMisReservasAlumnoCanceladas:
                Toast.makeText(this, "Canceladas", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_menuMisReservasAlumnoAceptadas:
                Toast.makeText(this, "Aceptadas", Toast.LENGTH_SHORT).show();
                break;
        }
        dl.closeDrawer(GravityCompat.START);
        return true;
    }
}
