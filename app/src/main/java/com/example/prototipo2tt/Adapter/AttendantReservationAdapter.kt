package com.example.prototipo2tt.Adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils

import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView

import androidx.recyclerview.widget.RecyclerView
import com.example.prototipo2tt.Models.Reservation
import com.example.prototipo2tt.R

class AttendantReservationAdapter(
    private val context: Context,
    private val reservation: MutableList<Reservation>,
    private val itemClickListener: OnReservationClickListener
) : RecyclerView.Adapter<AttendantReservationAdapter.ViewHolder>() {

    interface OnReservationClickListener {
        fun onItemClick(reservation: Reservation)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val reservationId = view.findViewById(R.id.idTextViewReservation) as TextView
        private val student = view.findViewById(R.id.alumnoTextViewReservation) as TextView
        private val laboratory = view.findViewById(R.id.labTextViewReservation) as TextView
        private val status = view.findViewById(R.id.statusTextViewReservation) as TextView
        val cardView = view.findViewById(R.id.cardViewReservation) as CardView
        val context = view.context

        fun bind(reservation: Reservation) {
            reservationId.text =
                itemView.context.getString(R.string.item_reservation_id, reservation.idReservation)
            student.text =
                itemView.context.getString(R.string.item_reservation_alumno, reservation.student)
            laboratory.text = reservation.laboratory
            status.text =
                itemView.context.getString(R.string.item_reservation_status, reservation.status)
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