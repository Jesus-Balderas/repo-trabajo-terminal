package com.example.prototipo2tt.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.prototipo2tt.R;
import com.example.prototipo2tt.models.LoadingDialogBar;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class VisorPDFActivity extends AppCompatActivity {

    public final static long ONE_MEGABYTE = 1024 * 1024*20;
    private String laboratorioNombre;
    private PDFView pdfView;
    private LoadingDialogBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_pdfactivity);

        Toolbar toolbarLaboratoriesPDF = findViewById(R.id.toolbarVisorPDF);
        toolbarLaboratoriesPDF.setTitle(R.string.app_name);
        setSupportActionBar(toolbarLaboratoriesPDF);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        progressBar = new LoadingDialogBar(this);
        progressBar.ShowDialog("Cargando...");

        laboratorioNombre = getIntent().getStringExtra("laboratorioPDF");
        pdfView = findViewById(R.id.pdfView);
        pdfView.setMidZoom(1.75f);

        //Invocamos al FirebaseStorage
        FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
        StorageReference mFirebaseStorageRef = mFirebaseStorage.getReference().child("laboratorios");
        mFirebaseStorageRef.child(laboratorioNombre).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                progressBar.HideDialog();
                pdfView.fromBytes(bytes).load();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setMessage("Error al cargar el pdf.");
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