package com.example.medecinapp.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medecinapp.Controllers.PatientController;
import com.example.medecinapp.R;
import com.example.medecinapp.models.Patient;

import java.util.Calendar;

public class AddPatientActivity extends AppCompatActivity {

    EditText nomInput, prenomInput, dateNaissanceInput, emailInput, telephoneInput, adresseInput;
    Button saveBtn;

    PatientController patientController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        // Initialisation des vues
        nomInput = findViewById(R.id.nomInput);
        prenomInput = findViewById(R.id.prenomInput);
        dateNaissanceInput = findViewById(R.id.dateNaissanceInput);
        emailInput = findViewById(R.id.emailInput);
        telephoneInput = findViewById(R.id.telephoneInput);
        adresseInput = findViewById(R.id.adresseInput);
        saveBtn = findViewById(R.id.saveBtn);

        // Initialisation du controller
        patientController = new PatientController(this);

        // DatePicker pour le champ Date de naissance
        dateNaissanceInput.setFocusable(false);
        dateNaissanceInput.setClickable(true);
        //button ouvrur le clwndreie
        dateNaissanceInput.setOnClickListener(v -> showDatePicker());

        // Bouton enregistrer
        saveBtn.setOnClickListener(v -> savePatient());
    }

    // Méthode pour afficher le calendrier
    private void showDatePicker() {
        //recupere el date actuelel
        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    dateNaissanceInput.setText(date);
                }, year, month, day);

        datePickerDialog.show();
    }

    // Méthode pour enregistrer le patient
    private void savePatient() {
        String nom = nomInput.getText().toString().trim();
        String prenom = prenomInput.getText().toString().trim();
        String dateNaissance = dateNaissanceInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String telephone = telephoneInput.getText().toString().trim();
        String adresse = adresseInput.getText().toString().trim();

        // Vérification des champs obligatoires
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        // Création du patient
        Patient patient = new Patient(0, nom, prenom, dateNaissance, telephone, email, adresse);

        // Ajout via le controller
        long result = patientController.addPatient(patient);

        if (result != -1) {
            Toast.makeText(this, "Patient ajouté avec succès", Toast.LENGTH_SHORT).show();
            finish(); // Retour à l'écran précédent
        } else {
            Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
        }
    }
}
