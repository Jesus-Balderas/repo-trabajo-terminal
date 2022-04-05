package com.example.prototipo2tt.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.prototipo2tt.R
import java.util.*

class CreateReservationActivity : AppCompatActivity() {

    private lateinit var editTextDate : EditText

    //Nos muestra marcado la fecha seleccionada cuando se abre el calendario
    private val selectedCalendar = Calendar.getInstance()
    private lateinit var btnStep1 : Button
    private lateinit var btnStep2 : Button
    private lateinit var btnCreateReservation : Button
    private lateinit var cvStep1 : CardView
    private lateinit var cvStep2 : CardView
    private lateinit var cvStep3 : CardView
    private lateinit var spinnerLaboratories : Spinner
    private lateinit var spinnerEncargados : Spinner
    private lateinit var spinnerComputers : Spinner
    private lateinit var spinnerHours : Spinner
    private lateinit var tvConfirmLaboratory : TextView
    private lateinit var tvConfirmEncargado : TextView
    private lateinit var tvConfirmComputer : TextView
    private lateinit var tvConfirmDate : TextView
    private lateinit var tvConfirmHour : TextView


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
        bindUI()

        btnStep1.setOnClickListener {
            cvStep1.visibility = View.GONE
            cvStep2.visibility = View.VISIBLE
        }
        btnStep2.setOnClickListener {
            if (editTextDate.text.isEmpty()) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Fecha")
                builder.setMessage("Es necesario seleccionar una fecha para su reservación")
                builder.setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            } else {
                showReservationData()
                cvStep2.visibility = View.GONE
                cvStep3.visibility = View.VISIBLE
            }

        }

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

    private fun showReservationData() {
        tvConfirmLaboratory.text = spinnerLaboratories.selectedItem.toString()
        tvConfirmEncargado.text = spinnerEncargados.selectedItem.toString()
        tvConfirmComputer.text = spinnerComputers.selectedItem.toString()
        tvConfirmDate.text = editTextDate.text.toString()
        tvConfirmHour.text = spinnerHours.selectedItem.toString()

    }

    private fun bindUI (){
        btnStep1 = findViewById(R.id.btnStep1)
        btnStep2 = findViewById(R.id.btnStep2)
        btnCreateReservation = findViewById(R.id.btnCreateReservation)
        cvStep1 = findViewById(R.id.cvStep1)
        cvStep2 = findViewById(R.id.cvStep2)
        cvStep3 = findViewById(R.id.cvStep3)
        editTextDate = findViewById(R.id.editTextDate)
        spinnerLaboratories = findViewById(R.id.spinnerLaboratories)
        spinnerEncargados = findViewById(R.id.spinnerEncargados)
        spinnerComputers = findViewById(R.id.spinnerComputers)
        spinnerHours = findViewById(R.id.spinnerHours)
        tvConfirmLaboratory = findViewById(R.id.tvConfirmLaboratory)
        tvConfirmEncargado = findViewById(R.id.tvConfirmEncargado)
        tvConfirmComputer = findViewById(R.id.tvConfirmComputer)
        tvConfirmDate = findViewById(R.id.tvConfirmDate)
        tvConfirmHour = findViewById(R.id.tvConfirmHour)
    }

    fun onClickDate (@Suppress("UNUSED_PARAMETER") v: View?){

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

    override fun onBackPressed() {
        when {
            cvStep3.visibility == View.VISIBLE -> {
                cvStep3.visibility = View.GONE
                cvStep2.visibility = View.VISIBLE

            }
            cvStep2.visibility == View.VISIBLE -> {
                cvStep2.visibility = View.GONE
                cvStep1.visibility = View.VISIBLE

            }
            cvStep1.visibility == View.VISIBLE -> {

                val builder = AlertDialog.Builder(this)
                builder.setTitle("¿Estas seguro que deseas salir?")
                builder.setMessage("Si abandonas el registro los datos que habias ingresado se perderán.")
                builder.setPositiveButton("Sí, salir") { _, _ ->
                    finish()
                }
                builder.setNegativeButton("Continuar registro") { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }
        }

    }
}