package com.example.prototipo2tt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.prototipo2tt.R
import com.example.prototipo2tt.models.StudentReservation
import java.util.ArrayList

class StudentReservationAdapter(
    private val context: Context,
    private val itemClickListener: StudentReservationAdapter.OnStudentReservationClickListener
) : RecyclerView.Adapter<StudentReservationAdapter.ViewHolder>() {

    var studentReservation = ArrayList<StudentReservation>()

    interface OnStudentReservationClickListener {
        fun onItemClick(studentReservation: StudentReservation)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val tvIdStudentReservation = itemView.findViewById<TextView>(R.id.tvIdStudentReservation)
        private val tvLabStudentReservation = itemView.findViewById<TextView>(R.id.tvLabStudentReservation)
        private val tvEncargadoStudentReservation = itemView.findViewById<TextView>(R.id.tvEncargadoStudentReservation)
        private val tvDateStudentReservation = itemView.findViewById<TextView>(R.id.tvDateStudentReservation)
        private val tvHourStudentReservation = itemView.findViewById<TextView>(R.id.tvHourStudentReservation)
        private val tvPCStudentReservation = itemView.findViewById<TextView>(R.id.tvPCStudentReservation)
        private val tvCreatedAtStudentReservation = itemView.findViewById<TextView>(R.id.tvCreatedAtStudentReservation)
        private val tvStatusStudentReservation = itemView.findViewById<TextView>(R.id.tvStatusStudentReservation)
        val cvStudentReservation: CardView = itemView.findViewById<CardView>(R.id.cvStudentReservation)

        fun bind(studentReservation: StudentReservation) {
            tvIdStudentReservation.text =
                itemView.context.getString(R.string.item_student_reservation_id, studentReservation.id)
            tvLabStudentReservation.text = studentReservation.laboratory.name
            tvEncargadoStudentReservation.text = studentReservation.attendant.name
            tvDateStudentReservation.text =
                itemView.context.getString(R.string.item_student_reservation_date, studentReservation.date)
            tvHourStudentReservation.text =
                itemView.context.getString(R.string.item_student_reservation_hour, studentReservation.hour)
            tvStatusStudentReservation.text = studentReservation.status
            tvPCStudentReservation.text =
                itemView.context.getString(R.string.item_student_reservation_computer, studentReservation.computer.num_pc)
            tvCreatedAtStudentReservation.text =
                itemView.context.getString(R.string.item_student_reservation_createdAt, studentReservation.createdDate)
            itemView.findViewById<Button>(R.id.btnCancelStudentReservation).setOnClickListener {
                itemClickListener.onItemClick(studentReservation)
            }
        }
    }

    //Inflar la vista a partir del xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student_reservation, parent, false)
        return ViewHolder(view)
    }

    //Enlaza la data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = studentReservation[position]
        holder.bind(item)
        holder.cvStudentReservation.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.fade_transition
            )
        )
    }

    //Devuelve la cantidad de elementos
    override fun getItemCount(): Int {
        return studentReservation.size
    }
}