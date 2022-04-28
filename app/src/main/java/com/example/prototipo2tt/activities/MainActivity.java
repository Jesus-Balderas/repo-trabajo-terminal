package com.example.prototipo2tt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.prototipo2tt.R;
import com.example.prototipo2tt.activities.attendant.LoginEncargadoActivity;
import com.example.prototipo2tt.activities.student.LoginAlumnoActivity;

public class MainActivity extends AppCompatActivity {
    Button btnAlumno, btnEncargado;
    Intent itn, itn2;
    Toolbar toolb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUI();

        toolb = (Toolbar) findViewById(R.id.toolbarmain);
        setSupportActionBar(toolb);

        btnAlumno.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                itn = new Intent(MainActivity.this, LoginAlumnoActivity.class);
                startActivity(itn);

            }
        });

        btnEncargado.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                itn2 = new Intent(MainActivity.this, LoginEncargadoActivity.class);
                startActivity(itn2);

            }
        });

    }

    private void bindUI(){
        btnAlumno = (Button) findViewById(R.id.btnAlumno);
        btnEncargado = (Button) findViewById(R.id.btnEncargado);
    }
}