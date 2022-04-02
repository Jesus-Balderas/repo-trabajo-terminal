package com.example.prototipo2tt.activities

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.prototipo2tt.R
import java.util.*

class CreateReservationActivity : AppCompatActivity() {

    lateinit var editTextDate : EditText

    //Nos muestra marcado la fecha seleccionada cuando se abre el calendario
    private val selectedCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_reservation)

        //Agregando el Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarCreateReservation)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        //Desplegando el boton hacia atras
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)


        val btnNext = findViewById<Button>(R.id.btnNext)
        val cvStep1 = findViewById<CardView>(R.id.cvStep1)
        val cvStep2 = findViewById<CardView>(R.id.cvStep2)
        editTextDate = findViewById<EditText>(R.id.editTextDate)
        btnNext.setOnClickListener {
            cvStep1.visibility = View.GONE
            cvStep2.visibility = View.VISIBLE
        }
        val spinnerLaboratories = findViewById<Spinner>(R.id.spinnerLaboratories)
        val spinnerEncargados = findViewById<Spinner>(R.id.spinnerEncargados)
        val spinnerComputers = findViewById<Spinner>(R.id.spinnerComputers)
        val spinnerHours = findViewById<Spinner>(R.id.spinnerHours)
        val optionsLaboratories = arrayOf(
            "Laboratorio de Programación 1",
            "Laboratorio de Programación 2",
            "Laboratorio de Redes",
            "Laboratorios de Sistemas 1",
            "Laboratorios de Sistemas 2",
            "Laboratorios de Sistemas 3",
            "Laboratorios de Sistemas 4",
        )
        val optionsEncargados = arrayOf("Marco Antonio")
        val optionsComputers = arrayOf(
            "1","2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11","12", "13", "14", "15", "16", "17", "18", "19", "20")
        val optionsHours = arrayOf("07:00","08:30", "10:30", "12:00", "13:30", "15:00", "16:30", "18:30", "20:00")
        spinnerLaboratories.adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, optionsLaboratories)
        spinnerEncargados.adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, optionsEncargados)
        spinnerComputers.adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, optionsComputers)
        spinnerHours.adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, optionsHours)

    }

    fun onClickDate (v: View?){

        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { view, y, m, d ->
            //Toast.makeText(this, "$y-$m-$d", Toast.LENGTH_SHORT).show()
            selectedCalendar.set(y,m,d)
            editTextDate.setText(
                resources.getString(
                    R.string.date_format,
                    y,
                    m.twoDigits(),
                    d.twoDigits()
                )
            )
        }

        //Creamos una instancia del DatePickerDialog
        val datePickerDialog = DatePickerDialog(this, listener, year, month, dayOfMonth)

        //Creamos una nueva instancia de Calendar para establecer los limites
        val datePicker = datePickerDialog.datePicker
        val calendar = Calendar.getInstance()

        //Estableciendo los limites del calendario
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        datePicker.minDate = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_MONTH, 29)
        datePicker.maxDate = calendar.timeInMillis

        //Mostrando el datePickerDialog
        datePickerDialog.show()

    }

    //Declarando una extensionFunction
    private fun Int.twoDigits(): String {

        return if(this>= 10) this.toString() else "0$this"
    }
}