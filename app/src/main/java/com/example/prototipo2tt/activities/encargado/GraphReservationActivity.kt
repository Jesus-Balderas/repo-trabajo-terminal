package com.example.prototipo2tt.activities.encargado

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.prototipo2tt.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class GraphReservationActivity : AppCompatActivity() {
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
        setPieChart()
    }
    private fun setPieChart(){

        val pieChart = findViewById<PieChart>(R.id.pieChart)
        //xvalues
        val xValues = ArrayList<String>()
        xValues.add("Confirmadas")
        xValues.add("Rechazadas")
        xValues.add("Reservadas")

        val yValues = ArrayList<Float>()
        yValues.add(23.5f)
        yValues.add(33.5f)
        yValues.add(43.5f)

        //yvalues
        val pieChartEntry = ArrayList<PieEntry>()
        for ((i, item) in yValues.withIndex()){
            pieChartEntry.add(PieEntry(item, xValues[i]))
        }
        //colors
        val colors = ArrayList<Int>()
        colors.add(Color.GREEN)
        colors.add(Color.RED)
        colors.add(Color.BLUE)

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
        description.xOffset = 60f
        description.yOffset = 350f


        val legend: Legend = pieChart.legend
        legend.textSize = 15f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.yOffset = 50f

    }
}