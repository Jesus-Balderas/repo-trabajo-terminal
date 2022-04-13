package com.example.prototipo2tt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.prototipo2tt.R
import com.example.prototipo2tt.models.StudentReservation

class StudentReservationHistoryAdapter(

    private val context: Context
): RecyclerView.Adapter<StudentReservationHistoryAdapter.ViewHolder>() {

    var stdReservationHistory = ArrayList<StudentReservation>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvIdStdReservHistory = itemView.findViewById<TextView>(R.id.tvIdStudentReservationHistory)
        private val tvLabStdReservHistory = itemView.findViewById<TextView>(R.id.tvLabStudentReservationHistory)
        private val tvEncargadoStdReservHistory = itemView.findViewById<TextView>(R.id.tvEncargadoStudentReservationHistory)
        private val tvDateStdReservHistory = itemView.findViewById<TextView>(R.id.tvDateStudentReservationHistory)
        private val tvHourStdReservHistory = itemView.findViewById<TextView>(R.id.tvHourStudentReservationHistory)
        private val tvPCStdReservHistory = itemView.findViewById<TextView>(R.id.tvPCStudentReservationHistory)
        private val tvCreatedAtStdReservHistory = itemView.findViewById<TextView>(R.id.tvCreatedAtStudentReservationHistory)
        private val tvStatusStdReservHistory = itemView.findViewById<TextView>(R.id.tvStatusStudentReservationHistory)
        val cvStudentReservationHistory: CardView = itemView.findViewById<CardView>(R.id.cvStudentReservationHistory)

        fun bind(stdReservationHistory: StudentReservation){
            tvIdStdReservHistory.text =
                itemView.context.getString(R.string.item_student_reservation_id, stdReservationHistory.id)
            tvLabStdReservHistory.text = stdReservationHistory.laboratory.name
            tvEncargadoStdReservHistory.text = stdReservationHistory.attendant.name
            tvDateStdReservHistory.text =
                itemView.context.getString(R.string.item_student_reservation_date, stdReservationHistory.date)
            tvHourStdReservHistory.text =
                itemView.context.getString(R.string.item_student_reservation_hour, stdReservationHistory.hour)
            tvStatusStdReservHistory.text = stdReservationHistory.status
            tvPCStdReservHistory.text =
                itemView.context.getString(R.string.item_student_reservation_computer, stdReservationHistory.computer.num_pc)
            tvCreatedAtStdReservHistory.text =
                itemView.context.getString(R.string.item_student_reservation_createdAt, stdReservationHistory.createdDate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student_reservation_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = stdReservationHistory[position]
        holder.bind(item)
        holder.cvStudentReservationHistory.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.fade_transition
            )
        )
    }

    override fun getItemCount(): Int {
       return stdReservationHistory.size
    }
}