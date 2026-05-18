package com.example.medecinapp.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medecinapp.Controllers.PatientController;
import com.example.medecinapp.R;
import com.example.medecinapp.models.Patient;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

public class EditPatientActivity extends AppCompatActivity {

    EditText nomInput, prenomInput, dateInput, emailInput, telInput, adresseInput;
    MaterialButton updateBtn, deleteBtn, ordonnanceBtn, showOrdonnancesBtn;
    PatientController patientController;
    int patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient);

        // ================= Initialisation des vues =================
        nomInput = findViewById(R.id.nomInput);
        prenomInput = findViewById(R.id.prenomInput);
        dateInput = findViewById(R.id.dateInput);
        emailInput = findViewById(R.id.emailInput);
        telInput = findViewById(R.id.telInput);
        adresseInput = findViewById(R.id.adresseInput);

        updateBtn = findViewById(R.id.updatePatientBtn);
        deleteBtn = findViewById(R.id.deletePatientBtn);
        ordonnanceBtn = findViewById(R.id.ordonnanceBtn);
        showOrdonnancesBtn = findViewById(R.id.showOrdonnancesBtn);

        patientController = new PatientController(this);

        // ================= Récupération des infos du patient =================
        patientId = getIntent().getIntExtra("id", -1);
        nomInput.setText(getIntent().getStringExtra("nom"));
        prenomInput.setText(getIntent().getStringExtra("prenom"));
        dateInput.setText(getIntent().getStringExtra("date_naissance"));
        emailInput.setText(getIntent().getStringExtra("email"));
        telInput.setText(getIntent().getStringExtra("telephone"));
        adresseInput.setText(getIntent().getStringExtra("adresse"));

        // ================= Animation fade-in pour les champs =================
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_slide);
        nomInput.startAnimation(fadeIn);
        prenomInput.startAnimation(fadeIn);
        dateInput.startAnimation(fadeIn);
        emailInput.startAnimation(fadeIn);
        telInput.startAnimation(fadeIn);
        adresseInput.startAnimation(fadeIn);

        // ================= DatePicker pour Date de naissance =================
        dateInput.setFocusable(false);
        dateInput.setClickable(true);
        dateInput.setOnClickListener(v -> showDatePicker());

        // ================= Animation pulse pour les boutons et gestion actions =================
        MaterialButton[] buttons = {updateBtn, deleteBtn, ordonnanceBtn, showOrdonnancesBtn};
        for (MaterialButton btn : buttons) {
            btn.setOnClickListener(v -> {
                // Animation pulse
                v.animate().scaleX(1.05f).scaleY(1.05f).setDuration(150)
                        .withEndAction(() -> v.animate().scaleX(1f).scaleY(1f).setDuration(150).start())
                        .start();

                // Actions selon le bouton
                if (v.getId() == R.id.updatePatientBtn) {
                    updatePatient();
                } else if (v.getId() == R.id.deletePatientBtn) {
                    deletePatient();
                } else if (v.getId() == R.id.ordonnanceBtn) {
                    openAddOrdonnance();
                } else if (v.getId() == R.id.showOrdonnancesBtn) {
                    openShowOrdonnances();
                }
            });
        }
    }

    // ================= Méthodes =================
    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    dateInput.setText(date);
                }, year, month, day);
        dpd.show();
    }

    private void updatePatient() {
        String nom = nomInput.getText().toString().trim();
        String prenom = prenomInput.getText().toString().trim();
        String dateNaissance = dateInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String telephone = telInput.getText().toString().trim();
        String adresse = adresseInput.getText().toString().trim();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        Patient patient = new Patient(patientId, nom, prenom, dateNaissance, telephone, email, adresse);
        boolean success = patientController.updatePatient(patient);

        if (success) {
            Toast.makeText(this, "Le patient " + nom + " a été modifié avec succès !", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de la modification de " + nom, Toast.LENGTH_SHORT).show();
        }
    }

    private void deletePatient() {
        boolean success = patientController.deletePatient(patientId);
        String nom = nomInput.getText().toString();

        if (success) {
            Toast.makeText(this, "Le patient " + nom + " a été supprimé avec succès !", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de la suppression de " + nom, Toast.LENGTH_SHORT).show();
        }
    }

    private void openAddOrdonnance() {
        Intent intent = new Intent(this, AddOrdonnanceActivity.class);
        intent.putExtra("patient_id", patientId);
        intent.putExtra("patient_nom", nomInput.getText().toString());
        startActivity(intent);
    }

    private void openShowOrdonnances() {
        Intent intent = new Intent(this, ShowOrdonnancesActivity.class);
        intent.putExtra("patient_id", patientId);
        startActivity(intent);
    }
}
