package com.example.prototipo2tt.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prototipo2tt.R;
import com.example.prototipo2tt.adapter.LaboratoryPDFAdapter;
import com.example.prototipo2tt.models.LaboratoryPDF;
import com.example.prototipo2tt.models.LoadingDialogBar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import java.util.ArrayList;


public class LaboratoriesPDFActivity extends AppCompatActivity {

    private RecyclerView listPDFView;
    private ArrayList<LaboratoryPDF> laboratories;
    private StorageReference mStorageRef;
    private LoadingDialogBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratories_pdfactivity);

        Toolbar toolbarLaboratoriesPDF = findViewById(R.id.toolbarLaboratoriesPDF);
        toolbarLaboratoriesPDF.setTitle(R.string.app_name);
        setSupportActionBar(toolbarLaboratoriesPDF);

        progressBar = new LoadingDialogBar(this);
        progressBar.ShowDialog("Cargando...");

        listPDFView = findViewById(R.id.rvLaboratoriesPDF);

        laboratories = new ArrayList<>();

        //Inicialización del objeto en firebase Storage
        mStorageRef = FirebaseStorage.getInstance().getReference();

        //traigo la referencia del bucket donde tengo guardados los PDF's de firebase
        StorageReference ref = mStorageRef.child("laboratorios");

        //codigo para traer todos los titulos de los pdf's
        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                Log.e("PDF's", "Entrando a recuperar PDF's");
                for (StorageReference item : listResult.getItems()){
                    laboratories.add(new LaboratoryPDF(item.getName()+""));
                    Log.e("PDF: ----->>>>", item.getPath()+"-----"+item.getName());
                }

                //configuro el adaptador de la lista
                LaboratoryPDFAdapter laboratoryPDFAdapter = new LaboratoryPDFAdapter(laboratories, getApplicationContext(), new LaboratoryPDFAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(LaboratoryPDF item) {
                        final String pdf = item.getName();
                        Intent intentPDF = new Intent(getApplicationContext(), VisorPDFActivity.class);
                        intentPDF.putExtra("laboratorioPDF", pdf);
                        startActivity(intentPDF);
                    }
                });

                progressBar.HideDialog();

                //muestro el adaptador de la vista
                listPDFView.setHasFixedSize(true);
                listPDFView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                listPDFView.setAdapter(laboratoryPDFAdapter);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.HideDialog();

                AlertDialog.Builder builder = new AlertDialog.Builder(LaboratoriesPDFActivity.this);
                builder.setMessage("Ha ocurrido un error al cargar los PDF's de los laboratorios.");
                builder.setCancelable(true);
                builder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                finish();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}