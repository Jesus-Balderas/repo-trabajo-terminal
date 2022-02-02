package com.example.prototipo2tt.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototipo2tt.R
import com.example.prototipo2tt.adapter.ScheduleLaboratoryAdapter
import com.example.prototipo2tt.models.Laboratory

class ScheduleLaboratoryActivity : AppCompatActivity(), ScheduleLaboratoryAdapter.OnLaboratoryClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_laboratory)

        val recyclerView: RecyclerView = findViewById(R.id.laboratoryRecyclerView)

        //Configuración del adaptador
        val adapter = ScheduleLaboratoryAdapter(this, laboratories(),this)

        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    private fun laboratories(): MutableList<Laboratory>{

        val laboratories: MutableList<Laboratory> = ArrayList()
        laboratories.add(
            Laboratory(1, "Laboratorio de Programación 1", "1201", "1", "ABIERTO")
        )
        laboratories.add(
            Laboratory(2, "Laboratorio de Programación 2", "1201", "1", "ABIERTO")
        )
        laboratories.add(
            Laboratory(3, "Laboratorio de Redes", "1202", "1", "ABIERTO")
        )
        laboratories.add(
            Laboratory(4, "Laboratorio de Sistemas 1", "1203", "1", "ABIERTO")
        )
        laboratories.add(
            Laboratory(5, "Laboratorio de Sistemas 2", "1204", "2", "ABIERTO")
        )
        laboratories.add(
            Laboratory(6, "Laboratorio de Sistemas 3", "1205", "2", "ABIERTO")
        )
        laboratories.add(
            Laboratory(7, "Laboratorio de Sistemas 4", "1206", "2", "ABIERTO")
        )
        laboratories.add(
            Laboratory(8, "Laboratorio de Sistemas 5", "1207", "2", "ABIERTO")
        )

        return laboratories
    }

    override fun onItemClick(laboratory: Laboratory) {
        Toast.makeText(this, laboratory.name, Toast.LENGTH_SHORT).show()
    }
}