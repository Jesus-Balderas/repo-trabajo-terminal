package com.example.prototipo2tt.activities.attendant

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.prototipo2tt.PreferenceHelper
import com.example.prototipo2tt.PreferenceHelper.get
import com.example.prototipo2tt.R
import com.example.prototipo2tt.io.ApiService
import com.example.prototipo2tt.models.Chart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class GraphReservationActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {

        PreferenceHelper.customPrefs(this,"jwt-attendant")

    }
    private lateinit var laboratory : TextView
    private lateinit var classroom: TextView
    private lateinit var edifice: TextView
    private lateinit var status: TextView
    private lateinit var reject: TextView
    private lateinit var cancel: TextView
    private lateinit var finish: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graphic_reservations)

        //Agregando el Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarGraphicReservation)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        //Desplegando el boton hacia atras
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        bindUi()
        getReservationsChart()

    }

    private fun bindUi(){
        laboratory = findViewById(R.id.textViewLaboratorio)
        classroom = findViewById(R.id.textViewLabSalon)
        edifice = findViewById(R.id.textViewLabEdificio)
        status = findViewById(R.id.textViewLabEstado)
        reject = findViewById(R.id.tvRechazadas)
        cancel = findViewById(R.id.tvCanceladas)
        finish = findViewById(R.id.tvFinalizadas)
    }

    private fun getReservationsChart(){
        var countReject: Int = 0
        var countCancel: Int = 0
        var countFinish: Int = 0
        val jwt = preferences["jwt-attendant",""]
        val call = apiService.getReservationsChart("Bearer $jwt")
        call.enqueue(object : Callback<Chart>{
            override fun onResponse(call: Call<Chart>, response: Response<Chart>) {
                if (response.isSuccessful){
                    val chart = response.body()
                    laboratory.text = chart?.laboratory?.get(0)?.name
                    classroom.text = getString(R.string.salon_chart, chart?.laboratory?.get(0)?.classroom )
                    edifice.text = getString(R.string.edificio_chart, chart?.laboratory?.get(0)?.edifice)
                    status.text = chart?.laboratory?.get(0)?.status
                    reject.text = getString(R.string.rechazadas_chart,chart?.reject)
                    cancel.text = getString(R.string.canceladas_chart, chart?.cancel)
                    finish.text = getString(R.string.finalizadas_chart, chart?.finish)

                    if (chart != null) {
                        countReject = chart.reject
                        countCancel = chart.cancel
                        countFinish = chart.finish
                        setPieChart(countReject, countCancel, countFinish)
                    }

                }
            }

            override fun onFailure(call: Call<Chart>, t: Throwable) {
                Toast.makeText(this@GraphReservationActivity, "No se puedieron cargar los datos  de la bitacora.",
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setPieChart(reject: Int, cancel: Int, finish : Int){

        val pieChart = findViewById<PieChart>(R.id.pieChart)
        //xvalues
        val xValues = ArrayList<String>()
        xValues.add("Finalizadas")
        xValues.add("Rechazadas")
        xValues.add("Canceladas")

        val yValues = ArrayList<Float>()
        yValues.add(finish.toFloat())
        yValues.add(reject.toFloat())
        yValues.add(cancel.toFloat())

        //yvalues
        val pieChartEntry = ArrayList<PieEntry>()
        for ((i, item) in yValues.withIndex()){
            pieChartEntry.add(PieEntry(item, xValues[i]))
        }
        //colors
        val colors = ArrayList<Int>()
        colors.add(Color.GREEN)
        colors.add(Color.RED)
        colors.add(Color.parseColor("#FF5722"))

        //fill the chart
        val pieDataSet = PieDataSet(pieChartEntry, "")
        pieDataSet.valueTextSize = 20f
        pieDataSet.sliceSpace = 3f
        pieDataSet.colors = colors

        val data = PieData(pieDataSet)
        pieChart.data = data
        pieChart.animateY(2000)
        pieChart.holeRadius = 45f
        pieChart.setTransparentCircleAlpha(50)


        val description: Description = pieChart.description
        description.text = "Historial de Reservaciones"
        description.textSize = 15f
        description.xOffset = 50f
        description.yOffset = 400f


        val legend: Legend = pieChart.legend
        legend.textSize = 15f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.xOffset = -30f
        legend.yOffset = 50f
        legend.xEntrySpace = 30f

    }
}