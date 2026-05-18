package com.example.medecinapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medecinapp.Controllers.PatientController;
import com.example.medecinapp.R;
import com.example.medecinapp.activities.EditPatientActivity;
import com.example.medecinapp.models.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> implements Filterable {

    private final Context context;
    private final ArrayList<Patient> patientList;
    private final ArrayList<Patient> patientListFull;
    private final PatientController patientController;

    public PatientAdapter(Context context, ArrayList<Patient> patientList, PatientController patientController) {
        this.context = context;
        this.patientList = patientList;
        this.patientController = patientController;
        this.patientListFull = new ArrayList<>(patientList);
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_patient, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Patient patient = patientList.get(position);
        holder.nom.setText(patient.getNom());
        holder.prenom.setText(patient.getPrenom());

        // Clic sur la carte pour ouvrir EditPatientActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditPatientActivity.class);
            intent.putExtra("id", patient.getId());
            intent.putExtra("nom", patient.getNom());
            intent.putExtra("prenom", patient.getPrenom());
            intent.putExtra("date_naissance", patient.getDateNaissance());
            intent.putExtra("email", patient.getEmail());
            intent.putExtra("telephone", patient.getTelephone());
            intent.putExtra("adresse", patient.getAdresse());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public void updateList(ArrayList<Patient> newList) {
        patientList.clear();
        patientList.addAll(newList);
        patientListFull.clear();
        patientListFull.addAll(newList);
        notifyDataSetChanged();
    }

    // Filtre pour recherche
    @Override
    public Filter getFilter() {
        return patientFilter;
    }

    private final Filter patientFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Patient> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(patientListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Patient patient : patientListFull) {
                    if (patient.getNom().toLowerCase().contains(filterPattern) ||
                            patient.getPrenom().toLowerCase().contains(filterPattern) ||
                            patient.getEmail().toLowerCase().contains(filterPattern)) {
                        filteredList.add(patient);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            patientList.clear();
            patientList.addAll((List<Patient>) results.values);
            notifyDataSetChanged();
        }
    };

    // ViewHolder
    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView nom, prenom;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.tvPatientNom);
            prenom = itemView.findViewById(R.id.tvPatientPrenom);
        }
    }
}
