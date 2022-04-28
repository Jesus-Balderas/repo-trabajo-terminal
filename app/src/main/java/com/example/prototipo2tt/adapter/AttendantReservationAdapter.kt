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
import com.example.prototipo2tt.models.AttendantReservation

class AttendantReservationAdapter(
    private val context: Context,
    private val itemClickListener: OnReservationClickListener
) : RecyclerView.Adapter<AttendantReservationAdapter.ViewHolder>() {

    interface OnReservationClickListener {
        fun onItemClick(attendantReservation: AttendantReservation)
    }

    var attendantReservation = ArrayList<AttendantReservation>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val reservationId = view.findViewById(R.id.tvReservation) as TextView
        private val student = view.findViewById(R.id.tvStudentReservation) as TextView
        private val boleta = view.findViewById<TextView>(R.id.tvStudentBoleta)
        private val tvCreatedDate = view.findViewById<TextView>(R.id.tvCreatedAtAttendantReservation)
        val cardView = view.findViewById(R.id.cvAttendantReservation) as CardView
        val context = view.context

        fun bind(attendantReservation: AttendantReservation) {
            reservationId.text =
                itemView.context.getString(R.string.item_reservation_id, attendantReservation.id )
            student.text =
                attendantReservation.student.name
            boleta.text = attendantReservation.student.num_boleta
            tvCreatedDate.text = itemView.context.getString(R.string.item_student_reservation_createdAt, attendantReservation.createdDate)
            itemView.findViewById<Button>(R.id.btnDetailsReservation).setOnClickListener {
                itemClickListener.onItemClick(attendantReservation)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_attendant_reservation, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = attendantReservation[position]
        holder.bind(item)
        holder.cardView.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.fade_transition
            )
        )
    }

    override fun getItemCount(): Int {
        return attendantReservation.size
    }

}