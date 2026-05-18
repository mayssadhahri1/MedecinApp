package com.example.medecinapp.Controllers;

import android.content.Context;
import android.database.Cursor;
import com.example.medecinapp.DBHelper;
import com.example.medecinapp.models.Patient;

import java.util.ArrayList;

public class PatientController {

    private DBHelper dbHelper;

    public PatientController(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Ajouter un patient
    public long addPatient(Patient patient) {
        return dbHelper.insertPatient(patient);
    }

    // Mettre à jour un patient
    public boolean updatePatient(Patient patient) {
        return dbHelper.updatePatient(patient);
    }

    // Supprimer un patient
    public boolean deletePatient(int id) {
        return dbHelper.deletePatient(id);
    }

    // Récupérer tous les patients
    public ArrayList<Patient> getAllPatients() {
        ArrayList<Patient> list = new ArrayList<>();
        Cursor cursor = dbHelper.getAllPatients();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Patient p = new Patient(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nom")),
                        cursor.getString(cursor.getColumnIndexOrThrow("prenom")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date_naissance")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("telephone")),
                        cursor.getString(cursor.getColumnIndexOrThrow("adresse"))
                );
                list.add(p);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }
}
