
package com.example.prototipo2tt.activities.student
import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.example.prototipo2tt.PreferenceHelper
import com.example.prototipo2tt.PreferenceHelper.get
import com.example.prototipo2tt.R
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.io.response.StudentReservationResponse
import com.example.prototipo2tt.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class CreateReservationActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {

        PreferenceHelper.customPrefs(this,"jwt-student")

    }

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
    private lateinit var tvSelectHours : TextView
    private lateinit var tvNotFoundHours : TextView

    private lateinit var progressBar : LoadingDialogBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_reservation)

        //Agregando el Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarCreateReservation)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

        bindUI()

        progressBar = LoadingDialogBar(this)

        btnStep1.setOnClickListener {
            cvStep1.visibility = View.GONE
            cvStep2.visibility = View.VISIBLE
        }
        btnStep2.setOnClickListener {
            when {
                editTextDate.text.isEmpty() -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Fecha")
                    builder.setMessage("Es necesario seleccionar una fecha para su reservación")
                    builder.setPositiveButton("Ok") { dialog, _ ->
                        dialog.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()
                }
                spinnerHours.selectedItem.toString() == "00:00" -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Hora")
                    builder.setMessage("No selecciono una hora valida para su reservación")
                    builder.setPositiveButton("Ok") { dialog, _ ->
                        dialog.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()

                }
                else -> {

                    showReservationData()
                    cvStep2.visibility = View.GONE
                    cvStep3.visibility = View.VISIBLE
                }
            }

        }

        loadLaboratories()
        listenLaboratoriesChanges()
        listenDateChanges()

        btnCreateReservation.setOnClickListener {
            performStoreReservation()
        }

    }

    private fun performStoreReservation(){

        progressBar.ShowDialog("Cargando...")

        btnCreateReservation.isClickable = false
        val jwt = preferences["jwt-student",""]
        val authHeader = "Bearer $jwt"
        val laboratory = spinnerLaboratories.selectedItem as Laboratory
        val attendant = spinnerEncargados.selectedItem as Attendant
        val computer = spinnerComputers.selectedItem as Computer
        val date = tvConfirmDate.text.toString()
        val hour = tvConfirmHour.text.toString()

        val call = apiService.storeStudentReservations(
            authHeader, laboratory.id,
            attendant.id, computer.id,
            date, hour)

        call.enqueue(object : Callback<StudentReservationResponse>{
            override fun onResponse(
                call: Call<StudentReservationResponse>,
                response: Response<StudentReservationResponse>
            ) {
                if (response.isSuccessful){
                    val reservation = response.body()
                    if (reservation?.success == true){
                        progressBar.HideDialog()
                        isSuccessfulReservation()

                    } else {

                        progressBar.HideDialog()
                        showMessageReservation()
                        btnCreateReservation.isClickable = true
                    }

                }

            }

            override fun onFailure(call: Call<StudentReservationResponse>, t: Throwable) {
                progressBar.HideDialog()
                Toast.makeText(this@CreateReservationActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                btnCreateReservation.isClickable = true
            }

        })
    }

    private fun showMessageReservation(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Ya tienes una reservación para ese día en el laboratorio que seleccionaste." +
                "Selecciona otra fecha distinta.")
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun isSuccessfulReservation(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Tu reservación ha sido registrada correctamente, ya puedes verla en tus solicitudes.")
        builder.setPositiveButton("Ok") { _, _ ->
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun listenDateChanges() {
        //Escuchamos cambios cuando cambia la seleccion de la fecha
        editTextDate.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val laboratory = spinnerLaboratories.selectedItem as Laboratory
                loadHours(laboratory.id,editTextDate.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }

    private fun loadHours(laboratoryId : Int, date : String){
        if (date.isEmpty()) {
            return
        }
        progressBar.ShowDialog("Cargando...")
        val call = apiService.getHours(laboratoryId, date)
        call.enqueue(object : Callback<ScheduleHour> {
            override fun onResponse(call: Call<ScheduleHour>, response: Response<ScheduleHour>) {
                if (response.isSuccessful && response.body().toString().isNotEmpty()) {
                    tvSelectHours.visibility = View.GONE
                    tvNotFoundHours.visibility = View.GONE
                    val scheduleHours = response.body()
                    val hourOptions = ArrayList<String>()

                    if (scheduleHours?.one != "00:00"){
                        scheduleHours?.one?.let { hourOptions.add(it) }
                    }
                    if (scheduleHours?.two != "00:00"){
                        scheduleHours?.two?.let { hourOptions.add(it) }
                    }
                    if (scheduleHours?.three != "00:00"){
                        scheduleHours?.three?.let { hourOptions.add(it) }
                    }
                    if (scheduleHours?.four != "00:00"){
                        scheduleHours?.four?.let { hourOptions.add(it) }
                    }
                    if (scheduleHours?.five != "00:00"){
                        scheduleHours?.five?.let { hourOptions.add(it) }
                    }
                    if (scheduleHours?.six != "00:00"){
                        scheduleHours?.six?.let { hourOptions.add(it) }
                    }
                    if (scheduleHours?.seven != "00:00"){
                        scheduleHours?.seven?.let { hourOptions.add(it) }
                    }
                    if (scheduleHours?.eight != "00:00"){
                        scheduleHours?.eight?.let { hourOptions.add(it) }
                    }
                    if (scheduleHours?.nine != "00:00"){
                        scheduleHours?.nine?.let { hourOptions.add(it) }
                    }

                    /*val hoursOptions = arrayOf(
                        scheduleHours?.one, scheduleHours?.two, scheduleHours?.three,
                        scheduleHours?.four, scheduleHours?.five, scheduleHours?.six,
                        scheduleHours?.seven, scheduleHours?.eight, scheduleHours?.nine,
                    )*/

                    spinnerHours.adapter = ArrayAdapter<String>(
                        this@CreateReservationActivity,
                        android.R.layout.simple_list_item_1,
                        hourOptions
                    )
                    spinnerHours.visibility = View.VISIBLE
                    //Toast.makeText(this@CreateReservationActivity, "horas: $hours", Toast.LENGTH_SHORT).show()
                }
                progressBar.HideDialog()

            }
            //Toast.makeText(this, "laboratory: ${laboratoryId}, date: $date", Toast.LENGTH_SHORT).show()

            override fun onFailure(call: Call<ScheduleHour>, t: Throwable) {
                progressBar.HideDialog()
                tvSelectHours.visibility = View.GONE
                tvNotFoundHours.visibility = View.VISIBLE
                spinnerHours.visibility = View.GONE

                /*Toast.makeText(this@CreateReservationActivity,
                    "Ocurrió un problema al cargar las horas", Toast.LENGTH_SHORT).show()*/
            }

        })

    }

    private fun listenLaboratoriesChanges() {
        //Nos va a permitir escuchar cambios por cada item que es seleccionado
        spinnerLaboratories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                val laboratory = adapter?.getItemAtPosition(position) as Laboratory
                /*Toast.makeText(this@CreateReservationActivity, "id: ${laboratory.id}", Toast.LENGTH_SHORT).show()*/
                loadAttendants(laboratory.id)
                loadComputers(laboratory.id)
                loadHours(laboratory.id, editTextDate.text.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun loadAttendants(laboratoryId: Int) {
        progressBar.ShowDialog("Cargando...")
        val call = apiService.getAttendants(laboratoryId)
        call.enqueue(object: Callback<ArrayList<Attendant>>{
            override fun onResponse(
                call: Call<ArrayList<Attendant>>,
                response: Response<ArrayList<Attendant>>
            ) {
                if (response.isSuccessful){
                    val attendants = response.body()//ArrayList de Attendants
                    spinnerEncargados.adapter = ArrayAdapter<Attendant>(
                        this@CreateReservationActivity, android.R.layout.simple_list_item_1,
                        attendants as MutableList<Attendant>)
                }
                progressBar.HideDialog()

            }

            override fun onFailure(call: Call<ArrayList<Attendant>>, t: Throwable) {
                progressBar.HideDialog()
                Toast.makeText(this@CreateReservationActivity,
                    "Ocurrió un problema al cargar los encargados", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun loadComputers(laboratoryId: Int){

        val call = apiService.getComputers(laboratoryId)
        call.enqueue(object : Callback<ArrayList<Computer>>
        {
            override fun onResponse(
                call: Call<ArrayList<Computer>>,
                response: Response<ArrayList<Computer>>
            ) {
                if (response.isSuccessful){

                    val computers = response.body()
                    spinnerComputers.adapter = ArrayAdapter<Computer>(
                        this@CreateReservationActivity, android.R.layout.simple_list_item_1,
                        computers as MutableList<Computer>)

                }

            }

            override fun onFailure(call: Call<ArrayList<Computer>>, t: Throwable) {
                progressBar.HideDialog()
                Toast.makeText(this@CreateReservationActivity,
                    "Ocurrió un problema al cargar las computadoras", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadLaboratories() {

        progressBar.ShowDialog("Cargando...")
        val call = apiService.getLaboratories()
        //ObjectExpression
        call.enqueue(object: Callback<ArrayList<Laboratory>>{
            override fun onResponse(
                call: Call<ArrayList<Laboratory>>,
                response: Response<ArrayList<Laboratory>>
            ) {
                if (response.isSuccessful){
                    val laboratories = response.body()//ArrayList de Laboratories
                    spinnerLaboratories.adapter = ArrayAdapter<Laboratory>(
                        this@CreateReservationActivity, android.R.layout.simple_list_item_1,
                        laboratories as MutableList<Laboratory>)
                }
                progressBar.HideDialog()

            }
            override fun onFailure(call: Call<ArrayList<Laboratory>>, t: Throwable) {
                progressBar.HideDialog()
                Toast.makeText(this@CreateReservationActivity,
                    "No se pudieron cargar los laboratorios", Toast.LENGTH_SHORT).show()
                finish()
            }

        })

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
        tvSelectHours = findViewById(R.id.tvSelectHours)
        tvNotFoundHours = findViewById(R.id.tvNotFoundHours)
    }

    fun onClickDate (@Suppress("UNUSED_PARAMETER") v: View?){

        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
            //Toast.makeText(this, "$y-$m-$d", Toast.LENGTH_SHORT).show()
            selectedCalendar.set(y,m,d)
            editTextDate.setText(
                resources.getString(
                    R.string.date_format,
                    y,
                    (m+1).twoDigits(),
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