package com.example.prototipo2tt.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototipo2tt.R
import com.example.prototipo2tt.adapter.ScheduleLaboratoryAdapter
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.models.Laboratory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class ScheduleLaboratoryActivity : AppCompatActivity(){

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val adapter = ScheduleLaboratoryAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_laboratory)

        //Agregando el Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarScheduleLaboratories)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        //Desplegando el boton hacia atras
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView: RecyclerView = findViewById(com.example.prototipo2tt.R.id.laboratoryRecyclerView)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        getJSONLaboratories()

    }

    private fun getJSONLaboratories(){

        val call = apiService.getLaboratories()
        call.enqueue(object: Callback<ArrayList<Laboratory>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ArrayList<Laboratory>>,
                response: Response<ArrayList<Laboratory>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        adapter.laboratory = it
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            override fun onFailure(call: Call<ArrayList<Laboratory>>, t: Throwable) {

                Toast.makeText(this@ScheduleLaboratoryActivity, "No se puedieron cargar los horarios", Toast.LENGTH_SHORT).show()
            }

        })

    }

}