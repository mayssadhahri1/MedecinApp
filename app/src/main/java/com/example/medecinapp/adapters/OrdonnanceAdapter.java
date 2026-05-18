package com.example.medecinapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.medecinapp.R;
import com.example.medecinapp.models.Ordonnance;

import java.util.ArrayList;

public class OrdonnanceAdapter extends RecyclerView.Adapter<OrdonnanceAdapter.ViewHolder> {

    private ArrayList<Ordonnance> ordonnances;

    public OrdonnanceAdapter(ArrayList<Ordonnance> ordonnances) {
        this.ordonnances = ordonnances;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ordonnance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ordonnance o = ordonnances.get(position);
        holder.dateText.setText("Date: " + o.getDate());
        holder.medicamentsText.setText("Médicaments: " + o.getMedicaments());
        holder.instructionsText.setText("Instructions: " + o.getInstructions());
    }

    @Override
    public int getItemCount() {
        return ordonnances.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, medicamentsText, instructionsText;

        ViewHolder(View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.tvOrdonnanceDate);
            medicamentsText = itemView.findViewById(R.id.tvOrdonnanceMedicaments);
            instructionsText = itemView.findViewById(R.id.tvOrdonnanceInstructions);
        }
    }
}
