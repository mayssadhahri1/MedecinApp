package com.example.medecinapp.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medecinapp.R;
import com.example.medecinapp.models.RendezVous;

import java.util.ArrayList;
import java.util.List;

public class RendezVousAdapter extends RecyclerView.Adapter<RendezVousAdapter.RendezVousViewHolder> {

    public interface OnRendezVousAction {
        void onEdit(RendezVous rv);
        void onDelete(RendezVous rv);
    }

    private final Context context;
    private final List<RendezVous> rendezVousList;
    private final OnRendezVousAction listener;

    public RendezVousAdapter(Context context, List<RendezVous> rendezVousList, OnRendezVousAction listener) {
        this.context = context;
        this.rendezVousList = (rendezVousList != null) ? rendezVousList : new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public RendezVousViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rendezvous_card, parent, false);
        return new RendezVousViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RendezVousViewHolder holder, int position) {
        RendezVous rv = rendezVousList.get(position);

        holder.patientName.setText(rv.getPatientName());
        holder.dateTime.setText(rv.getDate() + " | " + rv.getStatut());

        // Définir la couleur du badge selon le statut
        switch (rv.getStatut()) {
            case "Confirmé":
                holder.statusIndicator.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                break;
            case "En attente":
                holder.statusIndicator.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFC107")));
                break;
            case "Urgent":
                holder.statusIndicator.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E53935")));
                break;
            default:
                holder.statusIndicator.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
        }

        if (listener != null) {
            holder.editBtn.setOnClickListener(v -> listener.onEdit(rv));
            holder.deleteBtn.setOnClickListener(v -> listener.onDelete(rv));
        }
    }

    @Override
    public int getItemCount() {
        return rendezVousList.size();
    }

    public void updateList(List<RendezVous> newList) {
        rendezVousList.clear();
        if (newList != null) rendezVousList.addAll(newList);
        notifyDataSetChanged();
    }

    static class RendezVousViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView patientName, dateTime;
        Button editBtn, deleteBtn;
        View statusIndicator;

        public RendezVousViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.rendezVousCard);
            patientName = itemView.findViewById(R.id.rendezVousPatient);
            dateTime = itemView.findViewById(R.id.rendezVousTime);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            statusIndicator = itemView.findViewById(R.id.statusIndicator);
        }
    }
}
