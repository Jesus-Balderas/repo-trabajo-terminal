package com.example.prototipo2tt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prototipo2tt.R;
import com.example.prototipo2tt.models.LaboratoryPDF;

import java.util.List;

public class LaboratoryPDFAdapter extends RecyclerView.Adapter<LaboratoryPDFAdapter.ViewHolder> {

    private List<LaboratoryPDF> mData;
    private final LayoutInflater mInflater;
    private final Context context;
    private static LaboratoryPDFAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(LaboratoryPDF item);
    }

    public LaboratoryPDFAdapter(List<LaboratoryPDF> itemList,Context context, LaboratoryPDFAdapter.OnItemClickListener listener) {
        this.mData = itemList;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvLaboratorioPDF);
            cardView = itemView.findViewById(R.id.cvItemLaboratoryPDF);

        }
        public void bindData(LaboratoryPDF item ){
            name.setText(item.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laboratory_pdf, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
