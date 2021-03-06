package com.example.prototipo2tt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prototipo2tt.R;
import com.example.prototipo2tt.activities.attendant.LoginEncargadoActivity;

public class RegistrarEncargadoActivity extends AppCompatActivity {

    EditText editTextNumeroEncargado, editTextNombresEncargado, editTextFirsNameEncargado;
    EditText editTextSecondNameEncargado, editTextEmailEncargado, editTextPassEncargado;
    EditText editTextConfPassEncargado;
    Button buttonRegisterEncargado;
    Button buttonGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_encargado);
        bindUI();
        Spinner spinner = (Spinner)findViewById(R.id.SpinnerLaboratorios);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.combo_laboratorios, R.layout.spinner_item_design);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),
                        "Selecionado: " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG).show();
                String laboratorio = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(RegistrarEncargadoActivity.this, LoginEncargadoActivity.class);
                startActivity(intent1);

            }
        });


    }

    private void bindUI(){
        editTextNumeroEncargado = (EditText) findViewById(R.id.editTextNumeroEncargado);
        editTextNombresEncargado = (EditText) findViewById(R.id.editTextNombresEncargado);
        editTextFirsNameEncargado = (EditText) findViewById(R.id.editTextFirstNameEncargado);
        editTextSecondNameEncargado = (EditText) findViewById(R.id.editTextSecondNameEncargado);
        editTextEmailEncargado = (EditText) findViewById(R.id.editTextEmailEncargado);
        editTextPassEncargado = (EditText) findViewById(R.id.editTextPassEncargado);
        editTextConfPassEncargado = (EditText) findViewById(R.id.editTextConfPassEncargado);
        buttonRegisterEncargado = (Button) findViewById(R.id.btnRegistrarEncargado);
        buttonGoToLogin = (Button) findViewById(R.id.btnBackToLogin);
    }

}