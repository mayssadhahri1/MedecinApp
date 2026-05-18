package com.example.medecinapp.activities;

import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medecinapp.Controllers.RendezVousController;
import com.example.medecinapp.R;
import com.example.medecinapp.adapters.RendezVousAdapter;
import com.example.medecinapp.models.RendezVous;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RendezVousActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private EditText searchRdv;
    private RendezVousAdapter adapter;
    private String selectedDate;

    private RendezVousController rendezVousController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendez_vous_calendar);

        // Initialisation
        rendezVousController = new RendezVousController(this);
        calendarView = findViewById(R.id.calendarView);
        recyclerView = findViewById(R.id.recyclerView);
        searchRdv = findViewById(R.id.searchRdv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton selectPatientBtn = findViewById(R.id.selectPatientBtn);
        selectPatientBtn.setOnClickListener(v -> {
            if (selectedDate == null) {
                Toast.makeText(this, "Sélectionnez d'abord une date", Toast.LENGTH_SHORT).show();
                return;
            }
            showAvailablePatientsDialog(selectedDate);
        });

        selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        loadRendezVous(selectedDate);

        // Changement de date
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            month += 1;
            selectedDate = String.format("%04d-%02d-%02d", year, month, dayOfMonth);
            loadRendezVous(selectedDate);
        });

        // Recherche en direct
    }

    private void loadRendezVous(String date) {
        ArrayList<RendezVous> list = rendezVousController.getRendezVousByDate(date);

        if (adapter == null) {
            adapter = new RendezVousAdapter(this, list, new RendezVousAdapter.OnRendezVousAction() {
                @Override
                public void onEdit(RendezVous rdv) {
                    String dateOnly = rdv.getDate().split(" ")[0];
                    showTimePickerForPatient(rdv.getPatientId(), rdv, dateOnly);
                }

                @Override
                public void onDelete(RendezVous rdv) {
                    new AlertDialog.Builder(RendezVousActivity.this)
                            .setTitle("Confirmation")
                            .setMessage("Voulez-vous vraiment supprimer ce rendez-vous ?")
                            .setPositiveButton("Oui", (dialog, which) -> {
                                rendezVousController.deleteRendezVous(rdv.getId());
                                loadRendezVous(selectedDate);
                                Toast.makeText(RendezVousActivity.this, "Rendez-vous supprimé", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("Non", null)
                            .show();
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateList(list);
        }
    }

    private void showTimePickerForPatient(int patientId, RendezVous rdvToUpdate, String date) {
        TimePickerDialog dialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            String dateTime = date + " " + String.format("%02d:%02d", hourOfDay, minute);

            if (rdvToUpdate == null && rendezVousController.patientHasRendezVous(patientId, date)) {
                Toast.makeText(this, "Ce patient a déjà un rendez-vous aujourd'hui", Toast.LENGTH_SHORT).show();
                return;
            }

            if (rdvToUpdate == null && rendezVousController.getRendezVousCountByDate(date) >= 10) {
                Toast.makeText(this, "Limite de 10 rendez-vous atteinte", Toast.LENGTH_SHORT).show();
                return;
            }

            if (rdvToUpdate == null && rendezVousController.isTimeSlotTaken(dateTime)) {
                Toast.makeText(this, "Cette heure est déjà réservée", Toast.LENGTH_SHORT).show();
                return;
            }

            if (rdvToUpdate == null) {
                RendezVous rdv = new RendezVous();
                rdv.setPatientId(patientId);
                rdv.setMedecinId(1);
                rdv.setDate(dateTime);
                rdv.setStatut("En attente");
                rdv.setRappel(1);
                rendezVousController.addRendezVous(rdv);
            } else {
                rdvToUpdate.setDate(dateTime);
                rendezVousController.updateRendezVous(rdvToUpdate);
            }

            loadRendezVous(date);
        }, 9, 0, true);

        dialog.setTitle("Choisir l'heure du rendez-vous");
        dialog.show();
    }

    private void showAvailablePatientsDialog(String date) {
        Cursor cursor = rendezVousController.getAvailablePatients(date);
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(this, "Aucun patient disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> patients = new ArrayList<>();
        ArrayList<Integer> patientIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            String patientName = cursor.getString(cursor.getColumnIndexOrThrow("nom")) + " " +
                    cursor.getString(cursor.getColumnIndexOrThrow("prenom"));
            int patientId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            patients.add(patientName);
            patientIds.add(patientId);
        }
        cursor.close();

        new AlertDialog.Builder(this)
                .setTitle("Sélectionnez un patient")
                .setAdapter(new android.widget.ArrayAdapter<>(this, android.R.layout.simple_list_item_1, patients),
                        (dialog, which) -> showTimePickerForPatient(patientIds.get(which), null, date))
                .setNegativeButton("Annuler", null)
                .show();
    }
}
