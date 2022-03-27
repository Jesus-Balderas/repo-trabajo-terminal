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
import com.example.prototipo2tt.models.Laboratory

class ScheduleLaboratoryAdapter(

    private val context: Context,
    private val itemClickListener: OnLaboratoryClickListener

): RecyclerView.Adapter<ScheduleLaboratoryAdapter.ViewHolder>() {

    interface OnLaboratoryClickListener {
        fun onItemClick(laboratory: Laboratory)
    }

    var laboratory = ArrayList<Laboratory>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name = view.findViewById<TextView>(R.id.textViewLaboratorio)
        private val classroom = view.findViewById<TextView>(R.id.textViewLabSalon)
        private val status = view.findViewById<TextView>(R.id.textViewLabEstado)
        private val edifice = view.findViewById<TextView>(R.id.textViewLabEdificio)
        val cardView: CardView = view.findViewById<CardView>(R.id.cardViewLaboratory)
        val context: Context = view.context

        fun bind(laboratory: Laboratory){

            name.text = laboratory.name
            classroom.text = itemView.context.getString(R.string.item_laboratory_classroom, laboratory.classroom)
            status.text = itemView.context.getString(R.string.item_reservation_status, laboratory.status)
            edifice.text = itemView.context.getString(R.string.item_laboratory_edifice, laboratory.edifice)
            itemView.findViewById<Button>(R.id.btnVerHorarioLab).setOnClickListener {
                itemClickListener.onItemClick(laboratory)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_laboratory, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = laboratory[position]
        holder.bind(item)
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition))

    }

    override fun getItemCount(): Int {
        return laboratory.size
    }

}