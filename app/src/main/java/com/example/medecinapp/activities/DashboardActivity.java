package com.example.medecinapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medecinapp.R;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout cardPatients, cardRendezvous, cardMessages, cardStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Lier les cartes avec leurs IDs
        cardPatients = findViewById(R.id.card_patients);
        cardRendezvous = findViewById(R.id.card_rendezvous);
        cardMessages = findViewById(R.id.card_contact);
        cardStats = findViewById(R.id.card_stats);

        // Cliquer sur Patients
        cardPatients.setOnClickListener(v -> {
            Log.d("DashboardActivity", "Card Patients clicked");
            startActivity(new Intent(DashboardActivity.this, PatientsActivity.class));
        });


        // Cliquer sur Rendez-vous
        cardRendezvous.setOnClickListener(v -> {
            Log.d("DashboardActivity", "Card Rendez-vous clicked");
            startActivity(new Intent(DashboardActivity.this, RendezVousActivity.class));
        });

        // Cliquer sur Messagerie
        cardMessages.setOnClickListener(v -> {
            Log.d("DashboardActivity", "Card Messagerie clicked");
            startActivity(new Intent(DashboardActivity.this, ContactActivity.class));
        });

        // Cliquer sur Statistiques
        cardStats.setOnClickListener(v -> {
            Log.d("DashboardActivity", "Card Statistiques clicked");
            startActivity(new Intent(DashboardActivity.this, StatsActivity.class));
        });
    }
}
