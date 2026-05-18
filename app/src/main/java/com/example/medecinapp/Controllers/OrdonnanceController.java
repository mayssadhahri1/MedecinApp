package com.example.medecinapp.Controllers;

import android.content.Context;

import com.example.medecinapp.DBHelper;
import com.example.medecinapp.models.Ordonnance;

import java.util.ArrayList;

public class OrdonnanceController {

    private DBHelper dbHelper;

    public OrdonnanceController(Context context) {
        dbHelper = new DBHelper(context);
    }

    // =================== AJOUT ===================
    public long addOrdonnance(int patientId, int medecinId, String date, String medicaments, String instructions) {
        Ordonnance o = new Ordonnance(0, patientId, medecinId, date, medicaments, instructions);
        return dbHelper.addOrdonnance(o);
    }

    public long addOrdonnance(Ordonnance o) {
        return dbHelper.addOrdonnance(o);
    }

    // =================== MODIFICATION ===================
    public boolean updateOrdonnance(Ordonnance o) {
        return dbHelper.updateOrdonnance(o);
    }

    // =================== SUPPRESSION ===================
    public boolean deleteOrdonnance(int id) {
        return dbHelper.deleteOrdonnance(id);
    }

    // =================== RECUPERATION ===================
    public ArrayList<Ordonnance> getAllOrdonnances() {
        return dbHelper.getAllOrdonnances();
    }

    // Méthode pour récupérer les ordonnances d’un patient
    public ArrayList<Ordonnance> getOrdonnancesByPatientList(int patientId) {
        return dbHelper.getOrdonnancesByPatientList(patientId);
    }

}
