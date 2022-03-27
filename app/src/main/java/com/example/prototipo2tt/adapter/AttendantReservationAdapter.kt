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
import com.example.prototipo2tt.models.Reservation
import com.example.prototipo2tt.R

class AttendantReservationAdapter(
    private val context: Context,
    private val reservation: ArrayList<Reservation>,
    private val itemClickListener: OnReservationClickListener
) : RecyclerView.Adapter<AttendantReservationAdapter.ViewHolder>() {

    interface OnReservationClickListener {
        fun onItemClick(reservation: Reservation)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val reservationId = view.findViewById(R.id.tvReservation) as TextView
        private val student = view.findViewById(R.id.tvStudentReservation) as TextView
        private val boleta = view.findViewById<TextView>(R.id.tvStudentBoleta)
        private val status = view.findViewById(R.id.tvStatusReservation) as TextView
        private val date = view.findViewById<TextView>(R.id.tvDateReservation)
        private val hour = view.findViewById<TextView>(R.id.tvTimeReservation)
        val cardView = view.findViewById(R.id.cardViewReservation) as CardView
        val context = view.context

        fun bind(reservation: Reservation) {
            reservationId.text =
                itemView.context.getString(R.string.item_reservation_id, reservation.id)
            student.text =
                itemView.context.getString(R.string.item_reservation_alumno, reservation.student)
            boleta.text = itemView.context.getString(R.string.item_reservation_boleta, reservation.boleta)
            status.text =
                itemView.context.getString(R.string.item_reservation_status, reservation.status)
            date.text = itemView.context.getString(R.string.item_reservation_date, reservation.date)
            hour.text = itemView.context.getString(R.string.item_reservation_hour, reservation.hour)
            itemView.findViewById<Button>(R.id.btnDetailsReservation).setOnClickListener {
                itemClickListener.onItemClick(reservation)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_attendant_reservation, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = reservation[position]
        holder.bind(item)
        holder.cardView.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.fade_transition
            )
        )
    }

    override fun getItemCount(): Int {
        return reservation.size
    }

}