package com.example.prototipo2tt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.prototipo2tt.R;

public class laboratorios_Alumno extends AppCompatActivity implements View.OnClickListener{

    CardView jcvLabProgramacon1, jcvLabProgramacon2, jcvLabProgramacon3, jcvLabProgramacon4, jcvLabSistemas1, jcvLabSistemas2, jcvLabRedes; //Java Card View

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_alumno);
        jcvLabProgramacon1 = (CardView) findViewById(R.id.cvLabProgramacion1);
        jcvLabProgramacon2 = (CardView) findViewById(R.id.cvLabProgramacion2);
        jcvLabProgramacon3 = (CardView) findViewById(R.id.cvLabProgramacion3);
        jcvLabProgramacon4 = (CardView) findViewById(R.id.cvLabProgramacion4);
        jcvLabSistemas1 = (CardView) findViewById(R.id.cvLabSistemas1);
        jcvLabSistemas2 = (CardView) findViewById(R.id.cvLabSistemas2);
        jcvLabRedes = (CardView) findViewById(R.id.cvLabRedes);

        jcvLabProgramacon1.setOnClickListener(this);
        jcvLabProgramacon2.setOnClickListener(this);
        jcvLabProgramacon3.setOnClickListener(this);
        jcvLabProgramacon4.setOnClickListener(this);
        jcvLabSistemas1.setOnClickListener(this);
        jcvLabSistemas2.setOnClickListener(this);
        jcvLabRedes.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.cvLabProgramacion1: i = new Intent(this, ReservaAlumno.class);startActivity(i); break;
            case R.id.cvLabProgramacion2: i = new Intent(this, ReservaAlumno.class);startActivity(i); break;
            case R.id.cvLabProgramacion3: i = new Intent(this, ReservaAlumno.class);startActivity(i); break;
            case R.id.cvLabProgramacion4: i = new Intent(this, ReservaAlumno.class);startActivity(i); break;
            case R.id.cvLabSistemas1: i = new Intent(this, ReservaAlumno.class);startActivity(i); break;
            case R.id.cvLabSistemas2: i = new Intent(this, ReservaAlumno.class);startActivity(i); break;
            case R.id.cvLabRedes: i = new Intent(this, ReservaAlumno.class);startActivity(i); break;
            default:break;
        }
    }


}
