package com.example.medecinapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medecinapp.Controllers.OrdonnanceController;
import com.example.medecinapp.R;
import com.example.medecinapp.models.Ordonnance;

import java.time.LocalDate;

public class AddOrdonnanceActivity extends AppCompatActivity {

    EditText medicamentsInput, instructionsInput;
    Button saveBtn;
    OrdonnanceController ordonnanceController;

    int patientId;
    String patientNom;
    int medecinId = 1; // Remplace par l'ID du médecin connecté si tu gères l'authentification

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ordonnance);

        // Initialisation du controller
        ordonnanceController = new OrdonnanceController(this);

        // Initialisation des vues
        medicamentsInput = findViewById(R.id.medicamentsInput);
        instructionsInput = findViewById(R.id.instructionsInput);
        saveBtn = findViewById(R.id.saveOrdonnanceBtn);

        // Récupérer l'ID et le nom du patient depuis l'intent
        patientId = getIntent().getIntExtra("patient_id", -1);
        patientNom = getIntent().getStringExtra("patient_nom");

        if (patientId == -1) {
            Toast.makeText(this, "Erreur : ID du patient invalide", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        saveBtn.setOnClickListener(v -> addOrdonnance());
    }

    private void addOrdonnance() {
        String medicaments = medicamentsInput.getText().toString().trim();
        String instructions = instructionsInput.getText().toString().trim();

        if (medicaments.isEmpty() || instructions.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crée l'objet Ordonnance
        Ordonnance ordonnance = new Ordonnance(
                0,
                patientId,
                medecinId,
                java.time.LocalDate.now().toString(),
                medicaments,
                instructions
        );

        // Passe l'objet au controller
        long result = ordonnanceController.addOrdonnance(ordonnance);

        if (result != -1) {
            Toast.makeText(this, "Ordonnance ajoutée pour " + patientNom, Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de l'ajout de l'ordonnance", Toast.LENGTH_SHORT).show();
        }
    }
}
