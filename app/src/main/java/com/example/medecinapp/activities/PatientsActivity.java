package com.example.medecinapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medecinapp.Controllers.PatientController;
import com.example.medecinapp.R;
import com.example.medecinapp.adapters.PatientAdapter;
import com.example.medecinapp.models.Patient;

import java.util.ArrayList;

public class PatientsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button addPatientBtn;
    private EditText searchPatient;

    private PatientController patientController;
    private ArrayList<Patient> patientList;
    private PatientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        // Initialisation des vues
        recyclerView = findViewById(R.id.patientRecyclerView);
        addPatientBtn = findViewById(R.id.addPatientBtn);
        searchPatient = findViewById(R.id.searchPatient);

        // Initialisation du controller et de la liste
        patientController = new PatientController(this);
        patientList = new ArrayList<>();

        // Adapter initialisé avec le PatientController
        adapter = new PatientAdapter(this, patientList, patientController);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Charger les patients depuis la DB
        loadPatients();

        // Ajouter un patient
        addPatientBtn.setOnClickListener(v -> startActivity(new Intent(this, AddPatientActivity.class)));

        // Filtrage en temps réel
        searchPatient.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void loadPatients() {
        ArrayList<Patient> patientsFromDB = patientController.getAllPatients();
        adapter.updateList(patientsFromDB);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPatients(); // Recharge la liste si un patient a été ajouté ou modifié
    }
}
