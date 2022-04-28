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

class StudentReservationAcceptAdapter(

    private val context: Context
): RecyclerView.Adapter<StudentReservationAcceptAdapter.ViewHolder>() {

    var stdReservationAccept = ArrayList<StudentReservation>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvIdStdReservAccept = itemView.findViewById<TextView>(R.id.tvIdStudentReservationAccept)
        private val tvLabStdReservAccept = itemView.findViewById<TextView>(R.id.tvLabStudentReservationAccept)
        private val tvEncargadoStdReservAccept = itemView.findViewById<TextView>(R.id.tvEncargadoStudentReservationAccept)
        private val tvDateStdReservAccept = itemView.findViewById<TextView>(R.id.tvDateStudentReservationAccept)
        private val tvHourStdReservAccept = itemView.findViewById<TextView>(R.id.tvHourStudentReservationAccept)
        private val tvPCStdReservAccept = itemView.findViewById<TextView>(R.id.tvPCStudentReservationAccept)
        private val tvCreatedAtStdReservAccept = itemView.findViewById<TextView>(R.id.tvCreatedAtStudentReservationAccept)
        private val tvStatusStdReservAccept = itemView.findViewById<TextView>(R.id.tvStatusStudentReservationAccept)
        val cvStudentReservationAccept: CardView = itemView.findViewById<CardView>(R.id.cvStudentReservationAccept)

        fun bind(stdReservationAccept: StudentReservation){
            tvIdStdReservAccept.text =
                itemView.context.getString(R.string.item_student_reservation_id, stdReservationAccept.id)
            tvLabStdReservAccept.text = stdReservationAccept.laboratory.name
            tvEncargadoStdReservAccept.text = stdReservationAccept.attendant.name
            tvDateStdReservAccept.text =
                itemView.context.getString(R.string.item_student_reservation_date, stdReservationAccept.date)
            tvHourStdReservAccept.text =
                itemView.context.getString(R.string.item_student_reservation_hour, stdReservationAccept.hour)
            tvStatusStdReservAccept.text = stdReservationAccept.status
            tvPCStdReservAccept.text =
                itemView.context.getString(R.string.item_student_reservation_computer, stdReservationAccept.computer.num_pc)
            tvCreatedAtStdReservAccept.text =
                itemView.context.getString(R.string.item_student_reservation_createdAt, stdReservationAccept.createdDate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student_reservation_accept, parent, false)
        return StudentReservationAcceptAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = stdReservationAccept[position]
        holder.bind(item)
        holder.cvStudentReservationAccept.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.fade_transition
            )
        )
    }

    override fun getItemCount(): Int {
        return stdReservationAccept.size
    }
}