package com.example.prototipo2tt.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.prototipo2tt.R;

public class login_profesor extends AppCompatActivity {

    EditText editTextNumEmpleado, editTextPasswordEncargado;
    Button btnLoginEncargado, btnRegisterEncargado, btnForgetPasswordEncargado;
    Toolbar toolb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_profesor);
        bindUi();

        toolb = (Toolbar) findViewById(R.id.toolbarmain);
        setSupportActionBar(toolb);

        btnLoginEncargado.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(login_profesor.this, profesor_home.class);
                startActivity(intent1);
                finish();
            }
        });

        btnRegisterEncargado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(login_profesor.this, registrar_encargado.class);
                startActivity(intent2);
            }
        });
    }

    private void bindUi(){
        btnLoginEncargado = (Button) findViewById(R.id.btnLoginEncargado);
        btnRegisterEncargado = (Button) findViewById(R.id.btnRegisterEncargado);
        btnForgetPasswordEncargado = (Button) findViewById(R.id.btnForgetPasswordEncargado);
        editTextNumEmpleado = (EditText) findViewById(R.id.editTextNumEmpleado);
        editTextPasswordEncargado = (EditText) findViewById(R.id.editTextPasswordEncargado);

    }

}
