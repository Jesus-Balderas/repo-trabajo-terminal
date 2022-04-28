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

class AttendantReservationAcceptAdapter(
    private val context: Context,
    private val itemClickListener : OnReservationAcceptClickListener
): RecyclerView.Adapter<AttendantReservationAcceptAdapter.ViewHolder>() {

    interface OnReservationAcceptClickListener {
        fun onItemClick(attendantReservAccept: AttendantReservation)
    }

    var attendantReservAccept = ArrayList<AttendantReservation>()

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        private val tvAttendantReservationAccept1 = itemView.findViewById<TextView>(R.id.tvAttendantReservationAccept1)
        private val tvAttendantReservationAccept2 = itemView.findViewById<TextView>(R.id.tvAttendantReservationAccept2)
        private val tvAttendantReservationAccept3 = itemView.findViewById<TextView>(R.id.tvAttendantReservationAccept3)
        private val tvAttendantReservationAccept4 = itemView.findViewById<TextView>(R.id.tvAttendantReservationAccept4)
        private val tvAttendantReservationAccept5 = itemView.findViewById<TextView>(R.id.tvAttendantReservationAccept5)
        private val tvAttendantReservationAccept6 = itemView.findViewById<TextView>(R.id.tvAttendantReservationAccept6)
        private val tvAttendantReservationAccept7 = itemView.findViewById<TextView>(R.id.tvAttendantReservationAccept7)
        private val tvAttendantReservationAccept8 = itemView.findViewById<TextView>(R.id.tvAttendantReservationAccept8)
        val cvAttendantReservationAccepted  : CardView = itemView.findViewById(R.id.cvAttendantReservationAccepted)

        fun bind(attendantReservAccept: AttendantReservation){
            tvAttendantReservationAccept1.text =
                itemView.context.getString(R.string.item_attendant_reservation_id, attendantReservAccept.id )
            tvAttendantReservationAccept2.text = attendantReservAccept.student.name
            tvAttendantReservationAccept3.text = attendantReservAccept.student.num_boleta
            tvAttendantReservationAccept4.text =
                itemView.context.getString(R.string.item_attendant_reservation_date, attendantReservAccept.date)
            tvAttendantReservationAccept5.text =
                itemView.context.getString(R.string.item_attendant_reservation_hour, attendantReservAccept.time)
            tvAttendantReservationAccept6.text = attendantReservAccept.status
            tvAttendantReservationAccept7.text =
                itemView.context.getString(R.string.item_attendant_reservation_computer, attendantReservAccept.computer.num_pc)
            tvAttendantReservationAccept8.text =
                itemView.context.getString(R.string.item_attendant_reservation_createdAt, attendantReservAccept.createdDate)

            itemView.findViewById<Button>(R.id.btnFinishReservation).setOnClickListener {
                itemClickListener.onItemClick(attendantReservAccept)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_attendant_reservation_accept, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = attendantReservAccept[position]
        holder.bind(item)
        holder.cvAttendantReservationAccepted.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.fade_transition
            )
        )
    }

    override fun getItemCount(): Int {

        return attendantReservAccept.size
    }

}