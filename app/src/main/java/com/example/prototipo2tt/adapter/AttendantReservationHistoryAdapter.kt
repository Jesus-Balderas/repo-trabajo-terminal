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
import com.example.prototipo2tt.models.AttendantReservation

class AttendantReservationHistoryAdapter(
    private val context: Context
): RecyclerView.Adapter<AttendantReservationHistoryAdapter.ViewHolder>() {

    var attendantReservHistory = ArrayList<AttendantReservation>()

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        private val tvAttendantReservationHistory1 = itemView.findViewById<TextView>(R.id.tvAttendantReservationHistory1)
        private val tvAttendantReservationHistory2 = itemView.findViewById<TextView>(R.id.tvAttendantReservationHistory2)
        private val tvAttendantReservationHistory3 = itemView.findViewById<TextView>(R.id.tvAttendantReservationHistory3)
        private val tvAttendantReservationHistory4 = itemView.findViewById<TextView>(R.id.tvAttendantReservationHistory4)
        private val tvAttendantReservationHistory5 = itemView.findViewById<TextView>(R.id.tvAttendantReservationHistory5)
        private val tvAttendantReservationHistory6 = itemView.findViewById<TextView>(R.id.tvAttendantReservationHistory6)
        private val tvAttendantReservationHistory7 = itemView.findViewById<TextView>(R.id.tvAttendantReservationHistory7)
        private val tvAttendantReservationHistory8 = itemView.findViewById<TextView>(R.id.tvAttendantReservationHistory8)
        val cvAttendantReservationHistory : CardView = itemView.findViewById(R.id.cvAttendantReservationHistory)

        fun bind(attendantReservHistory: AttendantReservation){
            tvAttendantReservationHistory1.text =
                itemView.context.getString(R.string.item_attendant_reservation_id, attendantReservHistory.id )
            tvAttendantReservationHistory2.text = attendantReservHistory.student.name
            tvAttendantReservationHistory3.text = attendantReservHistory.student.num_boleta
            tvAttendantReservationHistory4.text =
                itemView.context.getString(R.string.item_attendant_reservation_date, attendantReservHistory.date)
            tvAttendantReservationHistory5.text =
                itemView.context.getString(R.string.item_attendant_reservation_hour, attendantReservHistory.time)
            tvAttendantReservationHistory6.text = attendantReservHistory.status
            tvAttendantReservationHistory7.text =
                itemView.context.getString(R.string.item_attendant_reservation_computer, attendantReservHistory.computer.num_pc)
            tvAttendantReservationHistory8.text =
                itemView.context.getString(R.string.item_attendant_reservation_createdAt, attendantReservHistory.createdDate)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_attendant_reservation_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = attendantReservHistory[position]
        holder.bind(item)
        holder.cvAttendantReservationHistory.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.fade_transition
            )
        )

    }

    override fun getItemCount(): Int {
        return attendantReservHistory.size
    }
}