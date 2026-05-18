package com.example.medecinapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medecinapp.Controllers.OrdonnanceController;
import com.example.medecinapp.R;
import com.example.medecinapp.adapters.OrdonnanceAdapter;
import com.example.medecinapp.models.Ordonnance;

import java.util.ArrayList;

public class ShowOrdonnancesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrdonnanceAdapter adapter;
    private ArrayList<Ordonnance> ordonnancesList;
    private OrdonnanceController ordonnanceController;
    private int patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ordonnances);

        // Initialisation RecyclerView
        recyclerView = findViewById(R.id.ordonnancesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialisation Controller
        ordonnanceController = new OrdonnanceController(this);
        ordonnancesList = new ArrayList<>();

        // Récupération du patientId depuis l'Intent
        patientId = getIntent().getIntExtra("patient_id", -1);
        Log.d("ShowOrdonnances", "onCreate: patientId reçu = " + patientId);

        if (patientId == -1) {
            Toast.makeText(this, "ID du patient invalide", Toast.LENGTH_SHORT).show();
            Log.e("ShowOrdonnances", "Patient ID invalide, fermeture de l'activité");
            finish();
            return;
        }

        // Charger les ordonnances
        loadOrdonnances();
    }

    private void loadOrdonnances() {
        try {
            // Récupère la liste depuis le controller
            ordonnancesList = ordonnanceController.getOrdonnancesByPatientList(patientId);

            if (ordonnancesList.isEmpty()) {
                Log.d("ShowOrdonnances", "Aucune ordonnance trouvée pour patientId=" + patientId);
                Toast.makeText(this, "Aucune ordonnance trouvée", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("ShowOrdonnances", "Nombre d'ordonnances = " + ordonnancesList.size());

                // Initialiser l'adapter et lier au RecyclerView
                adapter = new OrdonnanceAdapter(ordonnancesList);
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception e) {
            Log.e("ShowOrdonnances", "Erreur récupération ordonnances: " + e.getMessage(), e);
            Toast.makeText(this, "Erreur lors de la récupération des ordonnances", Toast.LENGTH_LONG).show();
        }
    }
}
