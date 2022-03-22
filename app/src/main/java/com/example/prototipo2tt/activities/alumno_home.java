package com.example.prototipo2tt.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.prototipo2tt.models.Laboratory;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.prototipo2tt.R;

public class alumno_home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    DrawerLayout dl;
    NavigationView nv;
    Toolbar toolb;
    CardView cvHorarios, cvAgendar, cvReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alumno_home);

        dl = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        toolb = findViewById(R.id.toolbar);
        cvHorarios = (CardView) findViewById(R.id.cardViewHorariosAlumno);
        cvAgendar = (CardView) findViewById(R.id.cardViewAgendarAlumno);
        cvReserva = (CardView) findViewById(R.id.cardViewReservaAlumno);

        cvHorarios.setOnClickListener(this);
        cvAgendar.setOnClickListener(this);
        cvReserva.setOnClickListener(this);

        setSupportActionBar(toolb);

        //hide and show items
        Menu menu = nv.getMenu();
        menu.findItem(R.id.id_menulogin).setVisible(false);

        nv.bringToFront();
        ActionBarDrawerToggle tg = new ActionBarDrawerToggle(this, dl, toolb, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(tg);
        tg.syncState();

        nv.setNavigationItemSelectedListener(this);
        nv.setCheckedItem(R.id.id_menuhorario);

    }

    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.id_menuhorario:
                break;
            case R.id.id_menuagendar:
                /*
                Intent i = new Intent(MainActivity.this, Bus.class);
                startActivity(i);
                */
                break;
            case R.id.id_menureserva:
                Toast.makeText(this, "hola mundo bonito uwu", Toast.LENGTH_SHORT).show();
                break;

        }
        dl.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.cardViewHorariosAlumno: i = new Intent(this, Lab_Alumno.class);startActivity(i); break;
            case R.id.cardViewAgendarAlumno: i = new Intent(this, Lab_Alumno.class);startActivity(i); break;
            case R.id.cardViewReservaAlumno: i = new Intent(this, Lab_Alumno.class);startActivity(i); break;
            default:break;
        }
    }
}
